/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import nodes.Simple_Node;
import objects.Books;
import objects.User;
import org.Main;
import reading_files.JsonBlock;
import reading_files.JsonReading;
import structures.Simply_Linked_List;

/**
 *
 * @author Francisco Suarez
 */
public class Home extends javax.swing.JFrame {

	/**
	 * Creates new form Home
	 */
	public Home() {
		initComponents();
		this.setLocationRelativeTo(null);
		this.getDataUser();
		this.confNav();
		this.confBooks();
		this.setRemoveTable();
		this.setTextFields();
	}

	void confNav() {
		SetIconsComponents.getInstance().setImages("/img/nav.jpg", nav);
		SetIconsComponents.getInstance().setImages("/img/logo_nav.png", nav_logo);
		SetIconsComponents.getInstance().setImages("/img/book.png", lbl_library);
		SetIconsComponents.getInstance().setImages("/img/rep.png", lbl_reports);
		SetIconsComponents.getInstance().setImages("/img/log_out.png", btn_logOut);
		jComboBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.repaint();
	}

	//Metodo para configurar mis textField
	private ValidateJTextField validate_Txt;

	void setTextFields() {
		validate_Txt = new ValidateJTextField();
		validate_Txt.containsNumbers(txt_ISBN);
		validate_Txt.containsNumbers(txt_Year);
	}

	void confBooks() {
		model = new MyModel();
		tableBooks.setModel(model);
		model.addColumn("ISBN");
		model.addColumn("TITULO");
		model.addColumn("CATEGORIA");
		tableBooks.getColumnModel().getColumn(0).setPreferredWidth(60);
		tableBooks.getColumnModel().getColumn(1).setPreferredWidth(350);
		tableBooks.getColumnModel().getColumn(2).setPreferredWidth(100);
		tableBooks.getTableHeader().setFont(new Font("Berlin Sans FB", Font.PLAIN, 24));
		tableBooks.getTableHeader().setForeground(Color.WHITE);
		tableBooks.getTableHeader().setBackground(Color.DARK_GRAY);
		tableBooks.setFont(new Font("Berlin Sans FB", Font.PLAIN, 19));
		tableBooks.setBackground(Color.WHITE);
		jScrollPane4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		//Mostramos todas las categorias de libros
		viewCategories();
		//Mostramos todos los libros
		viewAllBooks();
	}

	void viewCategories() {
		this.jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{}));
		this.combo_Categories.setModel(new javax.swing.DefaultComboBoxModel(new String[]{}));
		this.combo_Delete_Category.setModel(new javax.swing.DefaultComboBoxModel(new String[]{}));
		this.jComboBox1.addItem("All");
		this.combo_Categories.addItem("Ninguna");
		this.combo_Delete_Category.addItem("Ninguna");
		this.jComboBox1.setSelectedIndex(0);
		this.combo_Categories.setSelectedIndex(0);
		this.combo_Delete_Category.setSelectedIndex(0);
		Simple_Node aux = Main.treeAVL.getCategories().getFirst();
		while (aux != null) {
			this.jComboBox1.addItem(aux.getData());
			this.combo_Categories.addItem(aux.getData());
			aux = aux.getNext();
		}
		aux = Main.treeAVL.getCategories(Main.user).getFirst();
		while (aux != null) {
			this.combo_Delete_Category.addItem(aux.getData());
			aux = aux.getNext();
		}
		this.repaint();
	}

	//Metodo que muestra todos los libros
	void viewAllBooks() {
		model.setRowCount(0);
		Simple_Node aux = null;
		if (this.jCheckBox1.isSelected()) {
			aux = Main.treeAVL.allBooks(Main.user).getFirst();
		} else {
			aux = Main.treeAVL.allBooks("").getFirst();
		}
		while (aux != null) {
			Books tmp = (Books) aux.getData();
			String[] data = {tmp.getISBN() + "", tmp.getTitle(), tmp.getCategory()};
			model.addRow(data);
			aux = aux.getNext();
		}
	}

	//Metodo para ver los libros de la categoria seleccionada
	void viewBooks(String category) {
		model.setRowCount(0);
		Simple_Node aux = null;
		if (this.jCheckBox1.isSelected()) {
			aux = Main.treeAVL.search(Main.treeAVL.getRoot(), category).listBooks(Main.user).getFirst();
		} else {
			aux = Main.treeAVL.search(Main.treeAVL.getRoot(), category).listBooks("").getFirst();
		}
		while (aux != null) {
			Books tmp = (Books) aux.getData();
			String[] data = {tmp.getISBN() + "", tmp.getTitle(), tmp.getCategory()};
			model.addRow(data);
			aux = aux.getNext();
		}
	}

	//Metodo para mostrar toda la informacion del libro
	void viewInfoBook(Books b) {
		info_Book.components(b.getISBN() + "", b.getTitle(), b.getAuthor(), b.getEditorial(), b.getYear(), b.getEdition(), b.getLenguage(), b.getUser());
	}

	//Procesos para eliminar (Panel eliminar)
	void setRemoveTable() {
		this.panel_Confirm_Delete.setVisible(false);
		model1 = new MyModel();
		tableRemove.setModel(model1);
		model1.addColumn("ISBN");
		model1.addColumn("TITULO");
		model1.addColumn("CATEGORIA");
		tableRemove.getColumnModel().getColumn(0).setPreferredWidth(60);
		tableRemove.getColumnModel().getColumn(1).setPreferredWidth(350);
		tableRemove.getColumnModel().getColumn(2).setPreferredWidth(100);
		tableRemove.getTableHeader().setFont(new Font("Berlin Sans FB", Font.PLAIN, 24));
		tableRemove.getTableHeader().setForeground(Color.WHITE);
		tableRemove.getTableHeader().setBackground(Color.DARK_GRAY);
		tableRemove.setFont(new Font("Berlin Sans FB", Font.PLAIN, 19));
		tableRemove.setBackground(Color.WHITE);
		jScrollPane1.getViewport().setBackground(Color.WHITE);
		jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		confRemove();
	}

	//Metodo que configura la tabla de elementos que puede eliminar el usuario
	void confRemove() {
		model1.setRowCount(0);
		Simple_Node aux = aux = Main.treeAVL.allBooks(Main.user).getFirst();
		while (aux != null) {
			Books tmp = (Books) aux.getData();
			String[] data = {tmp.getISBN() + "", tmp.getTitle(), tmp.getCategory()};
			model1.addRow(data);
			aux = aux.getNext();
		}
	}

	//Variable que refleja el libro a eliminar
	Books b_delete = null;

	//Metodo para mostrarle el libro que esta por eliminar
	void setRemoveBook(Books b) {
		//Informacion del libro
		String info = "<html>"
				+ "<p><b>- INFORMACION DEL LIBRO </b></p>"
				+ "<p><b>#ISBN: </b> " + b.getISBN() + "</p>"
				+ "<p><b>#TITULO: </b> " + b.getTitle() + "</p>"
				+ "<p><b>#AUTOR: </b> " + b.getAuthor() + "</p>"
				+ "<p><b>#EDITORIAL: </b> " + b.getEditorial() + "</p>"
				+ "<p><b>#EDICION: </b> " + b.getEdition() + " - <b>#AÑO: </b> " + b.getYear() + "</p>"
				+ "<p><b>#IDIOMA: </b> " + b.getLenguage() + "</p>"
				+ "</html>";
		this.lbl_RemoveBook.setText(info);
		//Se asiga el libro
		this.b_delete = b;
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgrond = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        btn_logOut = new javax.swing.JButton();
        nav_logo = new javax.swing.JLabel();
        lbl_reports = new javax.swing.JLabel();
        lbl_library = new javax.swing.JLabel();
        btn_profile = new javax.swing.JButton();
        btn_reports = new javax.swing.JButton();
        nav = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        panel_profile = new javax.swing.JPanel();
        txt_Name = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        lbl_carnet = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        txt_Surname = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jSeparator18 = new javax.swing.JSeparator();
        jLabel38 = new javax.swing.JLabel();
        txt_Career = new javax.swing.JTextField();
        jSeparator19 = new javax.swing.JSeparator();
        jLabel39 = new javax.swing.JLabel();
        txt_Password = new javax.swing.JTextField();
        jSeparator20 = new javax.swing.JSeparator();
        btn_ModifyAccount = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        btn_DeleteAccount = new javax.swing.JButton();
        btn_Block = new javax.swing.JButton();
        panel_AddBook = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        txt_ISBN = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txt_Title = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        txt_Year = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        txt_Author = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_Editorial = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        txt_Edition = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        btn_add_book = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JSeparator();
        btn_LoadBooks = new javax.swing.JButton();
        combo_Categories = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        txt_Lenguage = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        panel_DeleteBook = new javax.swing.JPanel();
        panel_Confirm_Delete = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbl_isbn_delete = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbl_title_delete = new javax.swing.JLabel();
        btn_cancelDelete = new javax.swing.JButton();
        btn_confirmDelete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_Delete = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableRemove = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        btn_deleteBook = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        txt_searchRemove = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        lbl_RemoveBook = new javax.swing.JLabel();
        panel_AddBook2 = new javax.swing.JPanel();
        txt_newCategory = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel26 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        btn_add_book2 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        btn_add_book3 = new javax.swing.JButton();
        combo_Delete_Category = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        info_Book = new views.components.Component_Books();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableBooks = new javax.swing.JTable();
        jLabel33 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel28 = new javax.swing.JLabel();
        txt_search = new javax.swing.JTextField();
        jSeparator13 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closingInstance(evt);
            }
        });

        backgrond.setBackground(new java.awt.Color(255, 255, 255));
        backgrond.setLayout(null);

        jComboBox1.setBackground(new java.awt.Color(51, 51, 51));
        jComboBox1.setFont(new java.awt.Font("Berlin Sans FB", 0, 26)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jComboBox1.setFocusable(false);
        jComboBox1.setOpaque(false);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        backgrond.add(jComboBox1);
        jComboBox1.setBounds(780, 350, 520, 43);

        btn_logOut.setBackground(new java.awt.Color(51, 51, 51));
        btn_logOut.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        btn_logOut.setForeground(new java.awt.Color(219, 170, 50));
        btn_logOut.setBorderPainted(false);
        btn_logOut.setContentAreaFilled(false);
        btn_logOut.setFocusPainted(false);
        btn_logOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logOutActionPerformed(evt);
            }
        });
        backgrond.add(btn_logOut);
        btn_logOut.setBounds(1630, 60, 90, 90);
        backgrond.add(nav_logo);
        nav_logo.setBounds(90, 10, 190, 170);
        backgrond.add(lbl_reports);
        lbl_reports.setBounds(710, 60, 80, 80);
        backgrond.add(lbl_library);
        lbl_library.setBounds(360, 60, 80, 80);

        btn_profile.setBackground(new java.awt.Color(51, 51, 51));
        btn_profile.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        btn_profile.setForeground(new java.awt.Color(219, 170, 50));
        btn_profile.setText("Biblioteca");
        btn_profile.setBorderPainted(false);
        btn_profile.setContentAreaFilled(false);
        btn_profile.setFocusPainted(false);
        backgrond.add(btn_profile);
        btn_profile.setBounds(430, 70, 240, 60);

        btn_reports.setBackground(new java.awt.Color(51, 51, 51));
        btn_reports.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        btn_reports.setForeground(new java.awt.Color(219, 170, 50));
        btn_reports.setText("Reportes");
        btn_reports.setBorderPainted(false);
        btn_reports.setContentAreaFilled(false);
        btn_reports.setFocusPainted(false);
        btn_reports.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openReports(evt);
            }
        });
        backgrond.add(btn_reports);
        btn_reports.setBounds(760, 70, 240, 60);
        backgrond.add(nav);
        nav.setBounds(-6, -5, 1780, 200);

        jTabbedPane2.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane2.setFocusable(false);
        jTabbedPane2.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N

        panel_profile.setBackground(new java.awt.Color(255, 255, 255));
        panel_profile.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_Name.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        txt_Name.setForeground(new java.awt.Color(153, 153, 153));
        txt_Name.setText("Francisco Luis");
        txt_Name.setBorder(null);
        txt_Name.setOpaque(false);
        panel_profile.add(txt_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 620, 60));

        jLabel34.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(64, 74, 84));
        jLabel34.setText("#NOMBRE");
        panel_profile.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, 30));

        jSeparator11.setBackground(new java.awt.Color(153, 153, 153));
        panel_profile.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 620, 20));

        lbl_carnet.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        lbl_carnet.setForeground(new java.awt.Color(153, 153, 153));
        lbl_carnet.setText("201807190");
        panel_profile.add(lbl_carnet, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, -1, -1));

        jSeparator17.setBackground(new java.awt.Color(153, 153, 153));
        panel_profile.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 150, 20));

        txt_Surname.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        txt_Surname.setForeground(new java.awt.Color(153, 153, 153));
        txt_Surname.setText("Suarez López");
        txt_Surname.setBorder(null);
        txt_Surname.setOpaque(false);
        panel_profile.add(txt_Surname, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 620, 60));

        jLabel37.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(64, 74, 84));
        jLabel37.setText("#APELLIDO");
        panel_profile.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, -1, 30));

        jSeparator18.setBackground(new java.awt.Color(153, 153, 153));
        panel_profile.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, 620, 20));

        jLabel38.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(64, 74, 84));
        jLabel38.setText("#CARRERA");
        panel_profile.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, -1, 30));

        txt_Career.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        txt_Career.setForeground(new java.awt.Color(153, 153, 153));
        txt_Career.setText("Ciencias y Sistemas");
        txt_Career.setBorder(null);
        txt_Career.setOpaque(false);
        panel_profile.add(txt_Career, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, 620, 60));

        jSeparator19.setBackground(new java.awt.Color(153, 153, 153));
        panel_profile.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 390, 620, 20));

        jLabel39.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(64, 74, 84));
        jLabel39.setText("#PASSWORD");
        panel_profile.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, -1, -1));

        txt_Password.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        txt_Password.setForeground(new java.awt.Color(153, 153, 153));
        txt_Password.setText("Contraseñav:");
        txt_Password.setBorder(null);
        txt_Password.setOpaque(false);
        txt_Password.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_PasswordFocusGained(evt);
            }
        });
        panel_profile.add(txt_Password, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 450, 610, 60));

        jSeparator20.setBackground(new java.awt.Color(153, 153, 153));
        panel_profile.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 500, 620, 20));

        btn_ModifyAccount.setBackground(new java.awt.Color(51, 51, 51));
        btn_ModifyAccount.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_ModifyAccount.setForeground(new java.awt.Color(255, 255, 255));
        btn_ModifyAccount.setText("Modificar");
        btn_ModifyAccount.setFocusPainted(false);
        btn_ModifyAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ModifyAccountActionPerformed(evt);
            }
        });
        panel_profile.add(btn_ModifyAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 530, 200, 60));

        jLabel40.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(64, 74, 84));
        jLabel40.setText("#CARNET");
        panel_profile.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, -1, -1));

        btn_DeleteAccount.setBackground(new java.awt.Color(102, 0, 0));
        btn_DeleteAccount.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_DeleteAccount.setForeground(new java.awt.Color(255, 255, 255));
        btn_DeleteAccount.setText("Eliminar cuenta");
        btn_DeleteAccount.setFocusPainted(false);
        btn_DeleteAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteAccountActionPerformed(evt);
            }
        });
        panel_profile.add(btn_DeleteAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 530, 220, 60));

        btn_Block.setBackground(new java.awt.Color(102, 0, 0));
        btn_Block.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_Block.setForeground(new java.awt.Color(255, 255, 255));
        btn_Block.setText("Generar Bloque");
        btn_Block.setFocusPainted(false);
        btn_Block.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BlockActionPerformed(evt);
            }
        });
        panel_profile.add(btn_Block, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 530, 220, 60));

        jTabbedPane2.addTab("Mi perfil", panel_profile);

        panel_AddBook.setBackground(new java.awt.Color(255, 255, 255));
        panel_AddBook.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setBackground(new java.awt.Color(153, 153, 153));
        panel_AddBook.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 200, 20));

        txt_ISBN.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        txt_ISBN.setForeground(new java.awt.Color(153, 153, 153));
        txt_ISBN.setBorder(null);
        txt_ISBN.setOpaque(false);
        panel_AddBook.add(txt_ISBN, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 180, 60));

        jLabel12.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(64, 74, 84));
        jLabel12.setText("#ISBN");
        panel_AddBook.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, -1));

        txt_Title.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        txt_Title.setForeground(new java.awt.Color(153, 153, 153));
        txt_Title.setBorder(null);
        txt_Title.setOpaque(false);
        panel_AddBook.add(txt_Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 620, 60));

        jLabel13.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(64, 74, 84));
        jLabel13.setText("#TITULO");
        panel_AddBook.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, -1, 30));

        jSeparator2.setBackground(new java.awt.Color(153, 153, 153));
        panel_AddBook.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 620, 20));

        txt_Year.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        txt_Year.setForeground(new java.awt.Color(153, 153, 153));
        txt_Year.setBorder(null);
        txt_Year.setOpaque(false);
        panel_AddBook.add(txt_Year, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 470, 230, 60));

        jLabel14.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(64, 74, 84));
        jLabel14.setText("#AÑO");
        panel_AddBook.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 440, -1, -1));

        jSeparator3.setBackground(new java.awt.Color(153, 153, 153));
        panel_AddBook.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 520, 220, 20));

        jLabel15.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(64, 74, 84));
        jLabel15.setText("#CATEGORIA");
        panel_AddBook.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, -1, -1));

        txt_Author.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        txt_Author.setForeground(new java.awt.Color(153, 153, 153));
        txt_Author.setBorder(null);
        txt_Author.setOpaque(false);
        panel_AddBook.add(txt_Author, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 620, 60));

        jLabel16.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(64, 74, 84));
        jLabel16.setText("#AUTOR");
        panel_AddBook.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, -1, 30));

        jLabel17.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(64, 74, 84));
        jLabel17.setText("#EDITORIAL");
        panel_AddBook.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, -1, 30));

        txt_Editorial.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        txt_Editorial.setForeground(new java.awt.Color(153, 153, 153));
        txt_Editorial.setBorder(null);
        txt_Editorial.setOpaque(false);
        panel_AddBook.add(txt_Editorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, 250, 60));

        jSeparator6.setBackground(new java.awt.Color(153, 153, 153));
        panel_AddBook.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 410, 260, 20));

        jLabel18.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(64, 74, 84));
        jLabel18.setText("#EDICION");
        panel_AddBook.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 440, -1, -1));

        txt_Edition.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        txt_Edition.setForeground(new java.awt.Color(153, 153, 153));
        txt_Edition.setBorder(null);
        txt_Edition.setOpaque(false);
        panel_AddBook.add(txt_Edition, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 470, 230, 60));

        jSeparator7.setBackground(new java.awt.Color(153, 153, 153));
        panel_AddBook.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 520, 220, 20));

        btn_add_book.setBackground(new java.awt.Color(51, 51, 51));
        btn_add_book.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_add_book.setForeground(new java.awt.Color(255, 255, 255));
        btn_add_book.setText("Agregar");
        btn_add_book.setFocusPainted(false);
        btn_add_book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_bookActionPerformed(evt);
            }
        });
        panel_AddBook.add(btn_add_book, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 560, 250, 60));

        jSeparator10.setBackground(new java.awt.Color(153, 153, 153));
        panel_AddBook.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, 620, 20));

        btn_LoadBooks.setBackground(new java.awt.Color(102, 0, 0));
        btn_LoadBooks.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_LoadBooks.setForeground(new java.awt.Color(255, 255, 255));
        btn_LoadBooks.setText("Realizar carga");
        btn_LoadBooks.setFocusPainted(false);
        btn_LoadBooks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LoadBooksActionPerformed(evt);
            }
        });
        panel_AddBook.add(btn_LoadBooks, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 560, 250, 60));

        combo_Categories.setBackground(new java.awt.Color(18, 30, 49));
        combo_Categories.setFont(new java.awt.Font("Berlin Sans FB", 0, 26)); // NOI18N
        combo_Categories.setForeground(new java.awt.Color(255, 255, 255));
        combo_Categories.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ninguna" }));
        combo_Categories.setFocusable(false);
        combo_Categories.setOpaque(false);
        panel_AddBook.add(combo_Categories, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 380, -1));

        jLabel20.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(64, 74, 84));
        jLabel20.setText("#IDIOMA");
        panel_AddBook.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 330, 220, 30));

        txt_Lenguage.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        txt_Lenguage.setForeground(new java.awt.Color(153, 153, 153));
        txt_Lenguage.setBorder(null);
        txt_Lenguage.setOpaque(false);
        panel_AddBook.add(txt_Lenguage, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 360, 350, 60));

        jSeparator8.setBackground(new java.awt.Color(153, 153, 153));
        panel_AddBook.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 410, 350, 20));

        jTabbedPane2.addTab("Añadir libro", panel_AddBook);

        panel_DeleteBook.setBackground(new java.awt.Color(255, 255, 255));
        panel_DeleteBook.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_Confirm_Delete.setBackground(new java.awt.Color(255, 255, 255));
        panel_Confirm_Delete.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(64, 74, 84));
        jLabel2.setText("- Escriba la razon");
        panel_Confirm_Delete.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 390, 20));

        lbl_isbn_delete.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        lbl_isbn_delete.setForeground(new java.awt.Color(153, 153, 153));
        lbl_isbn_delete.setText("00000");
        panel_Confirm_Delete.add(lbl_isbn_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(111, 50, 100, 26));

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(64, 74, 84));
        jLabel4.setText("#ISBN:");
        panel_Confirm_Delete.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 50, -1, 26));

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(64, 74, 84));
        jLabel3.setText("#TITULO:");
        panel_Confirm_Delete.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, -1, 26));

        lbl_title_delete.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        lbl_title_delete.setForeground(new java.awt.Color(153, 153, 153));
        lbl_title_delete.setText("00000");
        panel_Confirm_Delete.add(lbl_title_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, 400, 30));

        btn_cancelDelete.setBackground(new java.awt.Color(102, 0, 0));
        btn_cancelDelete.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_cancelDelete.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancelDelete.setText("Cancelar");
        btn_cancelDelete.setFocusPainted(false);
        btn_cancelDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelDeleteActionPerformed(evt);
            }
        });
        panel_Confirm_Delete.add(btn_cancelDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, 220, 40));

        btn_confirmDelete.setBackground(new java.awt.Color(51, 51, 51));
        btn_confirmDelete.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_confirmDelete.setForeground(new java.awt.Color(255, 255, 255));
        btn_confirmDelete.setText("Confirmar");
        btn_confirmDelete.setFocusPainted(false);
        btn_confirmDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_confirmDeleteActionPerformed(evt);
            }
        });
        panel_Confirm_Delete.add(btn_confirmDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 220, 40));

        txt_Delete.setBackground(new java.awt.Color(255, 255, 255));
        txt_Delete.setColumns(20);
        txt_Delete.setFont(new java.awt.Font("Berlin Sans FB", 0, 20)); // NOI18N
        txt_Delete.setRows(2);
        txt_Delete.setBorder(null);
        jScrollPane2.setViewportView(txt_Delete);

        panel_Confirm_Delete.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 540, 70));

        jLabel5.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(64, 74, 84));
        jLabel5.setText("- Razon por la que desea dar de baja:");
        panel_Confirm_Delete.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 12, 390, 26));

        panel_DeleteBook.add(panel_Confirm_Delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 740, 240));

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setOpaque(false);

        tableRemove.setBackground(new java.awt.Color(255, 255, 255));
        tableRemove.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        tableRemove.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableRemove.setRowHeight(30);
        tableRemove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectBookRemove(evt);
            }
        });
        jScrollPane3.setViewportView(tableRemove);

        panel_DeleteBook.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 640, 160));

        jLabel19.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(64, 74, 84));
        jLabel19.setText("#MIS LIBROS");
        panel_DeleteBook.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, -1));

        btn_deleteBook.setBackground(new java.awt.Color(51, 51, 51));
        btn_deleteBook.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_deleteBook.setForeground(new java.awt.Color(255, 255, 255));
        btn_deleteBook.setText("Eliminar");
        btn_deleteBook.setFocusPainted(false);
        btn_deleteBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteBookActionPerformed(evt);
            }
        });
        panel_DeleteBook.add(btn_deleteBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 550, 250, 60));

        jLabel27.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(64, 74, 84));
        jLabel27.setText("- Eliminar libro");
        panel_DeleteBook.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 360, 40));

        jLabel29.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(64, 74, 84));
        jLabel29.setText("#BUSCAR:");
        panel_DeleteBook.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 120, 30));

        jSeparator14.setBackground(new java.awt.Color(153, 153, 153));
        panel_DeleteBook.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 420, 20));

        txt_searchRemove.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        txt_searchRemove.setForeground(new java.awt.Color(153, 153, 153));
        txt_searchRemove.setBorder(null);
        txt_searchRemove.setOpaque(false);
        txt_searchRemove.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_searchRemoveFocusGained(evt);
            }
        });
        txt_searchRemove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchRemovesearchBook(evt);
            }
        });
        panel_DeleteBook.add(txt_searchRemove, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 430, 60));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setOpaque(false);

        lbl_RemoveBook.setBackground(new java.awt.Color(255, 255, 255));
        lbl_RemoveBook.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        lbl_RemoveBook.setForeground(new java.awt.Color(64, 74, 84));
        jScrollPane1.setViewportView(lbl_RemoveBook);

        panel_DeleteBook.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 400, 640, 140));

        jTabbedPane2.addTab("Eliminar libro", panel_DeleteBook);

        panel_AddBook2.setBackground(new java.awt.Color(255, 255, 255));
        panel_AddBook2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_newCategory.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        txt_newCategory.setForeground(new java.awt.Color(153, 153, 153));
        txt_newCategory.setBorder(null);
        txt_newCategory.setOpaque(false);
        panel_AddBook2.add(txt_newCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 620, 60));

        jLabel23.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(64, 74, 84));
        jLabel23.setText("- Eliminar categoria");
        panel_AddBook2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 440, 50));

        jSeparator12.setBackground(new java.awt.Color(153, 153, 153));
        panel_AddBook2.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 620, 20));

        jLabel26.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(64, 74, 84));
        jLabel26.setText("#CATEGORIA");
        panel_AddBook2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, -1, 30));

        jSeparator15.setBackground(new java.awt.Color(153, 153, 153));
        panel_AddBook2.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 480, 510, 20));

        btn_add_book2.setBackground(new java.awt.Color(51, 51, 51));
        btn_add_book2.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_add_book2.setForeground(new java.awt.Color(255, 255, 255));
        btn_add_book2.setText("Eliminar");
        btn_add_book2.setFocusPainted(false);
        btn_add_book2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_book2ActionPerformed(evt);
            }
        });
        panel_AddBook2.add(btn_add_book2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 510, 250, 60));

        jLabel24.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(64, 74, 84));
        jLabel24.setText("#NOMBRE");
        panel_AddBook2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, 30));

        jLabel25.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(64, 74, 84));
        jLabel25.setText("- Crear categoria");
        panel_AddBook2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 360, 50));

        btn_add_book3.setBackground(new java.awt.Color(51, 51, 51));
        btn_add_book3.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        btn_add_book3.setForeground(new java.awt.Color(255, 255, 255));
        btn_add_book3.setText("Crear");
        btn_add_book3.setFocusPainted(false);
        btn_add_book3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_book3ActionPerformed(evt);
            }
        });
        panel_AddBook2.add(btn_add_book3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 250, 60));

        combo_Delete_Category.setBackground(new java.awt.Color(51, 51, 51));
        combo_Delete_Category.setFont(new java.awt.Font("Berlin Sans FB", 0, 26)); // NOI18N
        combo_Delete_Category.setForeground(new java.awt.Color(255, 255, 255));
        combo_Delete_Category.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ninguna" }));
        combo_Delete_Category.setFocusable(false);
        combo_Delete_Category.setOpaque(false);
        panel_AddBook2.add(combo_Delete_Category, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 430, 530, -1));

        jTabbedPane2.addTab("Categorias", panel_AddBook2);

        backgrond.add(jTabbedPane2);
        jTabbedPane2.setBounds(-10, 210, 760, 680);

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(64, 74, 84));
        jLabel1.setText("Biblioteca Virtual");
        backgrond.add(jLabel1);
        jLabel1.setBounds(1070, 210, 360, 50);
        backgrond.add(info_Book);
        info_Book.setBounds(1430, 350, 325, 525);

        jLabel21.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(64, 74, 84));
        jLabel21.setText("#BUSCAR:");
        backgrond.add(jLabel21);
        jLabel21.setBounds(760, 460, 120, 30);

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setOpaque(false);

        tableBooks.setBackground(new java.awt.Color(255, 255, 255));
        tableBooks.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        tableBooks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableBooks.setRowHeight(30);
        tableBooks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectBook(evt);
            }
        });
        jScrollPane4.setViewportView(tableBooks);

        backgrond.add(jScrollPane4);
        jScrollPane4.setBounds(770, 510, 640, 360);

        jLabel33.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(64, 74, 84));
        jLabel33.setText("#SELECCIONA UNA CATEGORIA");
        backgrond.add(jLabel33);
        jLabel33.setBounds(760, 300, 370, 30);

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(64, 74, 84));
        jCheckBox1.setText("#SOLO MIS LIBROS");
        jCheckBox1.setFocusable(false);
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                aloneMyBooks(evt);
            }
        });
        backgrond.add(jCheckBox1);
        jCheckBox1.setBounds(1190, 300, 230, 35);

        jLabel28.setFont(new java.awt.Font("Berlin Sans FB", 0, 25)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(64, 74, 84));
        jLabel28.setText("#SELECCIONE UN LIBRO");
        backgrond.add(jLabel28);
        jLabel28.setBounds(760, 410, 370, 30);

        txt_search.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        txt_search.setForeground(new java.awt.Color(153, 153, 153));
        txt_search.setBorder(null);
        txt_search.setOpaque(false);
        txt_search.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_searchFocusGained(evt);
            }
        });
        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchBook(evt);
            }
        });
        backgrond.add(txt_search);
        txt_search.setBounds(880, 460, 430, 30);

        jSeparator13.setBackground(new java.awt.Color(153, 153, 153));
        backgrond.add(jSeparator13);
        jSeparator13.setBounds(880, 490, 420, 20);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backgrond, javax.swing.GroupLayout.PREFERRED_SIZE, 1773, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgrond, javax.swing.GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
		// TODO add your handling code here:
		this.changeCategory();
    }//GEN-LAST:event_jComboBox1ActionPerformed

	private void changeCategory() {
		if (jComboBox1.getSelectedItem().toString().equals("All")) {
			viewAllBooks();
		} else {
			viewBooks(jComboBox1.getSelectedItem().toString());
		}
	}

    private void selectBook(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectBook
		// TODO add your handling code here:
		int row = tableBooks.rowAtPoint(evt.getPoint());
		if (row > -1) {
			String isbn = String.valueOf(this.tableBooks.getValueAt(row, 0));
			String category = String.valueOf(this.tableBooks.getValueAt(row, 2));
			Books b = Main.treeAVL.getBook(category, Integer.parseInt(isbn));
			viewInfoBook(b);
			this.repaint();
		}
    }//GEN-LAST:event_selectBook

    private void aloneMyBooks(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_aloneMyBooks
		// TODO add your handling code here:
		this.changeCategory();
    }//GEN-LAST:event_aloneMyBooks

    private void txt_searchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_searchFocusGained
		// TODO add your handling code here:
    }//GEN-LAST:event_txt_searchFocusGained

    private void searchBook(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchBook
		// TODO add your handling code here:
		TableRowSorter trs = new TableRowSorter(this.tableBooks.getModel());
		tableBooks.setRowSorter(trs);
		trs.setRowFilter(RowFilter.regexFilter("(i?)" + this.txt_search.getText()));
    }//GEN-LAST:event_searchBook

    private void txt_searchRemoveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_searchRemoveFocusGained
		// TODO add your handling code here:
    }//GEN-LAST:event_txt_searchRemoveFocusGained

    private void txt_searchRemovesearchBook(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchRemovesearchBook
		// TODO add your handling code here:
		TableRowSorter trs = new TableRowSorter(this.tableRemove.getModel());
		tableRemove.setRowSorter(trs);
		trs.setRowFilter(RowFilter.regexFilter("(i?)" + this.txt_searchRemove.getText()));
    }//GEN-LAST:event_txt_searchRemovesearchBook

    private void selectBookRemove(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectBookRemove
		// TODO add your handling code here:
		int row = tableRemove.rowAtPoint(evt.getPoint());
		if (row > -1) {
			this.panel_Confirm_Delete.setVisible(false);
			this.jScrollPane1.setVisible(true);
			this.btn_deleteBook.setVisible(true);
			String isbn = String.valueOf(this.tableRemove.getValueAt(row, 0));
			String category = String.valueOf(this.tableRemove.getValueAt(row, 2));
			Books b = Main.treeAVL.getBook(category, Integer.parseInt(isbn));
			setRemoveBook(b);
			this.repaint();
		}
    }//GEN-LAST:event_selectBookRemove

    private void btn_deleteBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteBookActionPerformed
		// TODO add your handling code here:
		if (b_delete != null) {
			this.jScrollPane1.setVisible(false);
			this.btn_deleteBook.setVisible(false);
			this.panel_Confirm_Delete.setVisible(true);
			this.lbl_isbn_delete.setText(b_delete.getISBN() + "");
			this.lbl_title_delete.setText(b_delete.getTitle());
		} else {
			Alerts a = new Alerts();
			a.define("No se ha seleccionado ningun libro.");
			a.setVisible(true);
		}
    }//GEN-LAST:event_btn_deleteBookActionPerformed

    private void btn_confirmDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_confirmDeleteActionPerformed
		// TODO add your handling code here:
		Alerts a = new Alerts();
		if (!this.txt_Delete.getText().equals("")) {
			this.panel_Confirm_Delete.setVisible(false);
			this.jScrollPane1.setVisible(true);
			this.btn_deleteBook.setVisible(true);
			Main.treeAVL.removeBook(b_delete.getCategory(), b_delete.getISBN());
			a.define("El libro fue eliminado con exito.");
			confRemove();
			changeCategory();
			this.jScrollPane1.setVisible(false);
			b_delete = null;
		} else {
			a.define("Ingrese una razon por la cual eliminar.");
		}
		this.txt_Delete.setText("");
		a.setVisible(true);
    }//GEN-LAST:event_btn_confirmDeleteActionPerformed

    private void btn_cancelDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelDeleteActionPerformed
		// TODO add your handling code here:
		this.panel_Confirm_Delete.setVisible(false);
		this.jScrollPane1.setVisible(true);
		this.btn_deleteBook.setVisible(true);
    }//GEN-LAST:event_btn_cancelDeleteActionPerformed

    private void btn_add_bookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_bookActionPerformed
		// TODO add your handling code here:
		Alerts a = new Alerts();
		String isbn = txt_ISBN.getText();
		String year = txt_Year.getText();
		String lenguage = txt_Lenguage.getText();
		String title = txt_Title.getText();
		String editorial = txt_Editorial.getText();
		String author = txt_Author.getText();
		String edition = txt_Edition.getText();
		boolean flag = validateTXTs(new String[]{isbn, title, author, editorial, year, edition, lenguage});
		if (flag && combo_Categories.getSelectedIndex() != 0) {
			Books b = new Books(isbn, title, author, editorial, year, edition, combo_Categories.getSelectedItem().toString(), lenguage, Main.user);
			if (Main.treeAVL.insert(b)) {
				resetFormInsertBook(new javax.swing.JTextField[]{txt_ISBN, txt_Year, txt_Lenguage, txt_Title, txt_Edition, txt_Editorial, txt_Author});
				combo_Categories.setSelectedIndex(0);
				changeCategory();
				confRemove();
				a.define("Libro insertado correctamente.");
			} else {
				a.define("El ISBN insertado ya existe en la coleccion de libros.");
			}
		} else {
			a.define("Existen campos invalidos, por favor inserte los datos necesarios.");
		}
		a.setVisible(true);
    }//GEN-LAST:event_btn_add_bookActionPerformed

    private void btn_add_book2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_book2ActionPerformed
		// TODO add your handling code here:
		Alerts a = new Alerts();
		if (combo_Delete_Category.getSelectedIndex() == 0) {
			a.define("Seleccione una categoria para proceder a eliminar.");
		} else {
			Main.treeAVL.remove(combo_Delete_Category.getSelectedItem().toString());
			viewCategories();
			viewAllBooks();
			confRemove();
			a.define("La categoria a sido eliminada con exito.");
		}
		a.setVisible(true);
    }//GEN-LAST:event_btn_add_book2ActionPerformed

    private void btn_add_book3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_book3ActionPerformed
		// TODO add your handling code here:
		Alerts a = new Alerts();
		String category = this.txt_newCategory.getText();
		boolean flag = validateTXTs(new String[]{category});
		if (flag) {
			if (Main.treeAVL.insert(new Books("-1", "", "", "", "", "", category, "", Main.user))) {
				resetFormInsertBook(new javax.swing.JTextField[]{txt_newCategory});
				viewCategories();
				viewAllBooks();
				confRemove();
				a.define("La categoria a sido agregada con exito.");
			} else {
				a.define("La categoria ya existe.");
			}
		} else {
			a.define("Por favor ingrese un nombre valido");
		}
		a.setVisible(true);
    }//GEN-LAST:event_btn_add_book3ActionPerformed

    private void btn_ModifyAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ModifyAccountActionPerformed
		// TODO add your handling code here:
		Alerts a = new Alerts();
		String name = txt_Name.getText();
		String surname = txt_Surname.getText();
		String career = txt_Career.getText();
		String password = txt_Password.getText();
		boolean flag = validateTXTs(new String[]{name, surname, career, password});
		if (flag) {
			Main.hashTable.edit(new User(Integer.parseInt(Main.user), name, surname, career, password, password));
			this.getDataUser();
		} else {
			a.define("Existen campos invalidos");
		}
    }//GEN-LAST:event_btn_ModifyAccountActionPerformed

    private void txt_PasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_PasswordFocusGained
		// TODO add your handling code here:
    }//GEN-LAST:event_txt_PasswordFocusGained
	//Metodo para cerrar sesion
	void logOut() {
		Main.user = "";
		dispose();
		Login log = new Login();
		log.setVisible(true);
	}

    private void btn_logOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logOutActionPerformed
		// TODO add your handling code here:
		logOut();
    }//GEN-LAST:event_btn_logOutActionPerformed

    private void btn_LoadBooksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LoadBooksActionPerformed
		// TODO add your handling code here:
		FileDialog dialog = new FileDialog(this, "Seleccione un archivo", FileDialog.LOAD);
		dialog.setBounds(WIDTH / 2, HEIGHT / 2, 800, 800);
		dialog.setVisible(true);
		dialog.setLocationRelativeTo(null);
		if (dialog.getFile() != null) {
			String directory = dialog.getDirectory();
			String filename = dialog.getFile();
			String route = directory + filename;
			JsonReading.getInstance().jsonBooks(route);
			viewCategories();
			viewAllBooks();
			confRemove();
		} else {
			System.out.println("No Seleccionó Archivo");
		}
    }//GEN-LAST:event_btn_LoadBooksActionPerformed

    private void btn_DeleteAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteAccountActionPerformed
		// TODO add your handling code here:
		//Procedemos a eliminar al usuario de la hash Table
		Main.hashTable.remove(Integer.parseInt(Main.user));

		//Cerramos el home
		logOut();
    }//GEN-LAST:event_btn_DeleteAccountActionPerformed

    private void btn_BlockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BlockActionPerformed
		// TODO add your handling code here:
		JsonBlock.getInstance().createBlock();
    }//GEN-LAST:event_btn_BlockActionPerformed

    private void openReports(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openReports
		// TODO add your handling code here:
		Reports_UI r = new Reports_UI();
		r.setVisible(true);
		dispose();
    }//GEN-LAST:event_openReports

    private void closingInstance(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closingInstance
		// TODO add your handling code here:
		//Cuando se cierre la instancia procedemos a eliminar de la lista este nodo de la red
		Settings_Form.getInstance().deleteNetwork();
		//Seteamos la lista de nodos en la red
		Settings_Form.getInstance().setNetworks();
    }//GEN-LAST:event_closingInstance

	//Metodo para traer del usuario loguado
	public void getDataUser() {
		User user = Main.hashTable.search(Integer.parseInt(Main.user));
		lbl_carnet.setText(user.getCarnet() + "");
		txt_Name.setText(user.getName());
		txt_Surname.setText(user.getSurname());
		txt_Career.setText(user.getCareer());
		txt_Password.setText(user.getPassword());
	}

	public boolean validateTXTs(String[] txts) {
		//Obtenemos los textos
		for (String txt : txts) {
			String aux = txt.trim();
			if (aux.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public void resetFormInsertBook(javax.swing.JTextField[] txts) {
		//Obtenemos los textos
		for (JTextField txt : txts) {
			txt.setText("");
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Home().setVisible(true);
			}
		});
	}

	//Objeto para setear el modelo de nuestra tabla para que no sea editable
	public class MyModel extends DefaultTableModel {

		public boolean isCellEditable(int row, int column) {
			// Aquí devolvemos true o false según queramos que una celda
			// identificada por fila,columna (row,column), sea o no editable
			return false;
		}
	}

	private MyModel model;
	private MyModel model1;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgrond;
    private javax.swing.JButton btn_Block;
    private javax.swing.JButton btn_DeleteAccount;
    private javax.swing.JButton btn_LoadBooks;
    private javax.swing.JButton btn_ModifyAccount;
    private javax.swing.JButton btn_add_book;
    private javax.swing.JButton btn_add_book2;
    private javax.swing.JButton btn_add_book3;
    private javax.swing.JButton btn_cancelDelete;
    private javax.swing.JButton btn_confirmDelete;
    private javax.swing.JButton btn_deleteBook;
    private javax.swing.JButton btn_logOut;
    private javax.swing.JButton btn_profile;
    private javax.swing.JButton btn_reports;
    private javax.swing.JComboBox combo_Categories;
    private javax.swing.JComboBox combo_Delete_Category;
    private views.components.Component_Books info_Book;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lbl_RemoveBook;
    private javax.swing.JLabel lbl_carnet;
    private javax.swing.JLabel lbl_isbn_delete;
    private javax.swing.JLabel lbl_library;
    private javax.swing.JLabel lbl_reports;
    private javax.swing.JLabel lbl_title_delete;
    private javax.swing.JLabel nav;
    private javax.swing.JLabel nav_logo;
    private javax.swing.JPanel panel_AddBook;
    private javax.swing.JPanel panel_AddBook2;
    private javax.swing.JPanel panel_Confirm_Delete;
    private javax.swing.JPanel panel_DeleteBook;
    private javax.swing.JPanel panel_profile;
    private javax.swing.JTable tableBooks;
    private javax.swing.JTable tableRemove;
    private javax.swing.JTextField txt_Author;
    private javax.swing.JTextField txt_Career;
    private javax.swing.JTextArea txt_Delete;
    private javax.swing.JTextField txt_Edition;
    private javax.swing.JTextField txt_Editorial;
    private javax.swing.JTextField txt_ISBN;
    private javax.swing.JTextField txt_Lenguage;
    private javax.swing.JTextField txt_Name;
    private javax.swing.JTextField txt_Password;
    private javax.swing.JTextField txt_Surname;
    private javax.swing.JTextField txt_Title;
    private javax.swing.JTextField txt_Year;
    private javax.swing.JTextField txt_newCategory;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_searchRemove;
    // End of variables declaration//GEN-END:variables
}
