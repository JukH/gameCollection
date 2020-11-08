import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class gameCollectionDb {

	public static void main(String[] args) {

		ArrayList<Object[]> data = new ArrayList<Object[]>();
		
		
		
		try {

			// Luodaan tietokantayhteys
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/gamecollection", "root", "********");

			// Luodaan Statement-olio, joka keskustelee tietokannan kanssa
			Statement stmt = con.createStatement();

			// Luodaan tulosjoukko, johon sijoitetaan kyselyn tulos
			ResultSet rs = stmt.executeQuery("SELECT * FROM games");
			int i = 0;

			// Tulosjoukko käydään silmukassa läpi
			while (rs.next()) {
				data.add( new Object[] { rs.getString(1), rs.getString(2), rs.getString(3) } ); //tiedot taulukkoon
				i++;
			}
		
			con.close();
			// Varaudutaan virheisiin
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "An error occurred");
		}

		JScrollPane scrollPane = new JScrollPane();
		
		//Luodaan tablemodel ja tehdään asiaankuuluvat sarakkeet
		DefaultTableModel model = new DefaultTableModel(); 
		model.addColumn("Game");
		model.addColumn("Year");
		model.addColumn("Platform");

		JTable table = new JTable(model);


		//Tulostetaan taulukon data JTableen näkyville riveiksi
		for (int i=0; i < data.size(); i++ ) {
			model.addRow(data.get(i));
		}
		
		JFrame ikkuna = new JFrame(); //Luodaan ikkuna
		JPanel paneeli = new JPanel(); //Luodaan ikkunaan paneeli
		JButton add = new JButton("Add"); //add-napin luonti
		
		
		// TÄSTÄ ALKAA PÄÄIKKUNAN add-NAPIN KOODI
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				JFrame lisäys = new JFrame();   //Luodaan ikkuna
				lisäys.setSize(400, 200);
				lisäys.setTitle("Add game"); //Ikkunalle titteli
				lisäys.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Suljetaan ruksilla ikkuna
				lisäys.setVisible(true); //Ikkuna näkyväksi
				lisäys.setLocationRelativeTo(null); //Asetetaan ikkuna aukeamaan keskelle
				JTextField addGameTextField = new JTextField(); //Pelin nimi-tekstikentän luonti
				addGameTextField.setPreferredSize( new Dimension( 150, 20 ) );
				
				JTextField addYearTextField = new JTextField(); //Pelin vuosi-tekstikentän luonti
				addYearTextField.setPreferredSize( new Dimension( 150, 20 ) );
				
				JTextField addPlatformTextField = new JTextField(); //Alusta-tekstikenttä
				addPlatformTextField.setPreferredSize( new Dimension( 150, 20 ) );
				
				
				JButton addGame = new JButton("Add to database"); //Kirjan lisäys-nappi
				addGame.setPreferredSize(new Dimension(140, 24)); //napin koko
				
				//Luodaan useampi paneeli layouttia silmälläpitäen
				JPanel paneeli = new JPanel();
				JPanel paneeli1 = new JPanel();
				JPanel paneeli2 = new JPanel();
				JPanel masterPanel = new JPanel();
				JPanel flowPanel = new JPanel(new FlowLayout());
				
				JLabel gameName = new JLabel("Game:");
				gameName.setPreferredSize(new Dimension(100,24));
				JLabel gameYear = new JLabel("Year:");
				gameYear.setPreferredSize(new Dimension(100, 24));
				JLabel platform = new JLabel("Platform:");
				platform.setPreferredSize(new Dimension(100, 24));
				
				paneeli.add(gameName, BorderLayout.EAST);
				paneeli.add(addGameTextField, BorderLayout.WEST); // lisätään pelin nimi-kenttä
				paneeli1.add(gameYear, BorderLayout.EAST);
				paneeli1.add(addYearTextField, BorderLayout.WEST); // lisätään vuosi-kenttä
				paneeli2.add(platform, BorderLayout.EAST);
				paneeli2.add(addPlatformTextField, BorderLayout.WEST); // lisätään alusta-kenttä
				
				
				flowPanel.add(addGame); //Rakennellaan muotoilu
				masterPanel.add(paneeli, BorderLayout.NORTH);
				masterPanel.add(paneeli1, BorderLayout.CENTER);
				masterPanel.add(paneeli2, BorderLayout.SOUTH);
				lisäys.getContentPane().add(masterPanel, BorderLayout.CENTER);
				lisäys.getContentPane().add(flowPanel, BorderLayout.SOUTH);
				
				
				
				//Kirjan lisäysnapin toiminnot
				addGame.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
				try {
					//Alustetaan muuttujat joita tarvitaan kun kaivetaan data tekstikentistä
					String game = addGameTextField.getText();
					String year = addYearTextField.getText();
					String platform = addPlatformTextField.getText();
					ArrayList<Object[]> data1 = new ArrayList<Object[]>(); //Luodaan uusi array-lista jotta vanhat tiedot eivät tulostu tuplana lisäyksen jälkeen päivitettäessä
					//int year = Integer.parseInt(year);
					
					// Luodaan tietokantayhteys
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost/gamecollection", "root", "Ap976tvn!");

					// Luodaan Statement-olio, joka keskustelee tietokannan kanssa
					Statement stmt = con.createStatement();
					
					if(game != null || year != null || platform != null){ //lisätään ehto jotta kaikki kentät täytetään
					String query = " insert into games (game, year, console)"
					        + " values (?, ?, ?)";
					
					// Luodaan preparestatement datan muokkaamista varten tietokannassa
					PreparedStatement preparedStmt = con.prepareStatement(query);
				      preparedStmt.setString (1, game);
				      preparedStmt.setString (2, year);
				      preparedStmt.setString(3, platform);
				      
				   // Suoritetaan preparedstatement
				      preparedStmt.execute();

				      // Nakataan viesti että kirjan lisäys onnistui
				      JOptionPane.showMessageDialog(masterPanel, "Game added!");
				      lisäys.dispose();
				     
				      
					} else { //Varoitellaan jos tietoja addmättä
						JOptionPane.showMessageDialog(masterPanel, "Missing information!");
					}
					
						
					

					// Luodaan uudelleen tulosjoukko, johon sijoitetaan kyselyn tulos
					ResultSet rs = stmt.executeQuery("SELECT * FROM games");
					int i = 0;
					
					// Lisätään tietokannasta data arraylistiin
					while (rs.next()) {

						data1.add( new Object[] { rs.getString(1), rs.getString(2), rs.getString(3) } ); //tiedot arraylistiin
						i++;
					}
					
					// Tyhjennetään vanha taulu jottei data näy tuplana (kömpelö tapa päivittää?)	
					 DefaultTableModel model1=(DefaultTableModel)table.getModel();
			            int rc= model.getRowCount();
			            for(i = 0;i<rc;i++){
			                model1.removeRow(0);
			            }     
					
					con.close();
					
					// Tulostetaan tuore taulu pelilisäyksen jälkeen JTableen
					for (int x=0; x < data1.size(); x++ ) { 
						model.addRow(data1.get(x));
					}
					// Varaudutaan virheisiin
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "An error occurred!");
				}
			}
		});
			}
		});
		
		// Poistonapin toiminnallisuudet alkaa tästä
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int valittuRivi = table.getSelectedRow(); 
				
				// Otetaan talteen valittu rivi oliona (index 0 = kirjan nimi-sarake)
				Object id = table.getValueAt(valittuRivi, 0);
				
				//muunnetaan kiinniotettu olio Stringiksi (kirjan nimi)
				String olioMuunnettu = String.valueOf(id);
				
				// Poistetaan poistettu kirja myös JTablesta
				int modelRow = table.convertRowIndexToModel(valittuRivi);
				model.removeRow(modelRow);
				
				// Poistetaan valittu kirja nimen perusteella tietokannasta
				String poistoKäsky = "DELETE FROM games WHERE game = " + "'"+olioMuunnettu+"'" + ";";
				
				
				try {
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost/gamecollection", "root", "Ap976tvn!");
					
					// Luodaan Statement-olio, joka keskustelee tietokannan kanssa
					Statement stmt = con.createStatement();
					stmt.executeUpdate(poistoKäsky);
					stmt.close();
					con.close();
					JOptionPane.showMessageDialog(null, "'" + olioMuunnettu + "'" + " removed from database!");
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "An error occurred!");
					e1.printStackTrace();
				}
				


				
			}
		});
		JMenuBar menu = new JMenuBar();
		
		
		ikkuna.setTitle("Game Collection");
		ikkuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		paneeli.add(add);
		paneeli.add(delete);
		
		ikkuna.getContentPane().add(scrollPane, BorderLayout.CENTER);
		ikkuna.getContentPane().add(menu, BorderLayout.NORTH);
		
		JMenu mnNewMenu_1 = new JMenu("Tietoja ohjelmasta");
		menu.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("N\u00E4yt\u00E4 ohjelman tiedot");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String link = "https://github.com/JukH/School";
				JOptionPane.showMessageDialog(null,
                        "(C) Jukka Heikkinen 2018\n"
                        + "Tietokantaohjelma javan olio-ohjelmointikurssille.\n"
                        + link, 
                        "", 
                        JOptionPane.PLAIN_MESSAGE);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem);
		
		JMenuBar menuBar = new JMenuBar();
		menu.add(menuBar);
		ikkuna.getContentPane().add(paneeli, BorderLayout.SOUTH);
		
		scrollPane.setViewportView(table);

		ikkuna.pack();
		ikkuna.setLocationRelativeTo(null); //Asetetaan ikkuna aukeamaan keskelle
		ikkuna.setVisible(true);

	}

}

