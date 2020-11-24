//comparison remboursement
package jeuisnavant;

import java.awt.Color;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class JeuISNavant extends JPanel implements MouseListener {
    //Partie de Loris
    static double ptotale = 0;
    static boolean impots=false;
    static int day=1, action = 3, lvl, credit = 0,passe;
    static double a=500000, b=10, e=0, p=0, nbrhabitants=5;
    static boolean upgradei = false, banque = false;
    static double adaptative_pollution, ac = a, bc = b, ec = e, ptotalec = ptotale, nbrhabitantsc = nbrhabitants;
    
    //Creation des tableaux
    static double habitations[][]=new double [6][4];
    static double industriels[][]=new double [6][3];
    static double commerce[][]=new double [5][3];
    static double loisirs[][]=new double [6][3];
    static double administration[][]=new double [5][4];
    static int niveaux[]=new int[20];
    
    //++++++++++++++++++++++++++++++++++++++++++++PARTIE EMPRUNT+++++++++++++++++++++++++++++++++++++++
    
     //Fonction de comparaison
    public static void comparison (double ac, double bc, double ec, double ptotalec, double nbrhabitantsc, Graphics2D g)
    {
        if (ac<a)
        {
            g.setColor(Color.WHITE);
            g.fillRect(15, 35, 200, 20);
            Font font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("Argent en augmentation", 20, 50);
        }else if(ac>a)
        {
            g.setColor(Color.WHITE);
            g.fillRect(15, 35, 200, 20);
            Font font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("Argent en baisse", 20, 50);
        }
        if (bc<b)
        {
            g.setColor(Color.WHITE);
            g.fillRect(15, 50, 200, 20);
            Font font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("Bonheur en augmentation", 20, 65);
        }else if(bc>b)
        {
            g.setColor(Color.WHITE);
            g.fillRect(15, 50, 200, 20);
            Font font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("Bonheur en baisse", 20, 65);
        }
        if (ec<e)
        {
            g.setColor(Color.WHITE);
            g.fillRect(15, 65, 200, 20);
            Font font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("Emploi en augmentation", 20, 80);
        }else if (ec>e)
        {
            g.setColor(Color.WHITE);
            g.fillRect(15, 65, 200, 20);
            Font font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("Emploi en baisse", 20, 80);
        }
        if (ptotalec<ptotale)
        {
            g.setColor(Color.WHITE);
            g.fillRect(15, 80, 200, 20);
            Font font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("Pollution en augmentation", 20, 95);
        }else if (ptotalec>ptotale)
        {
            g.setColor(Color.WHITE);
            g.fillRect(15, 80, 200, 20);
            Font font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("Pollution en baisse", 20, 95);
        }
        if (nbrhabitantsc<nbrhabitants)
        {
            g.setColor(Color.WHITE);
            g.fillRect(15, 95, 200, 20);
            Font font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("Nombre d'habitants en augmentation", 20, 110);
        }else if (nbrhabitantsc>nbrhabitants)
        {
            g.setColor(Color.WHITE);
            g.fillRect(15, 95, 200, 20);
            Font font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("Nombre d'habitants en baisse", 20, 110);
        } 
    }

    //Ameliorations
    public static void upgrades_indus ()
    {
        int indus0 = generate_number(1,4);
        int indus1 = generate_number(3,5);
        int indus2 = generate_number(1,3);
        
        //Case 4 augmentation de la pollution par jour
        industriels[4][0]=0.25*indus0;
        industriels[4][1]=0.35*indus1;
        industriels[4][2]=0.25*indus2;
    }
    
    //Fonction qui verifie si le montant emprunte est superieur a 200 000 fois le niveau du joueur
    public static boolean verif(int i, int lvl)
    {
        return i<=200000*lvl;
    }

    //Affichage du menu emprunt et actualisation du montant
    public static void emprunt(Graphics2D g)
    {
        //remise à 0 de l'affichage
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(longueur2, 0, longueur-longueur2, hauteur);
        
        g.setColor(java.awt.Color.decode("#000000"));
        
        if (verif(200000, lvl))
        {
            g.fillRect(longueur2 + 10, 25, 49, 49);
            g.drawString("200 000 €", longueur2 + 10, 85);
            credit = 1;
        }
        if (verif(400000, lvl))
        {
            g.fillRect(longueur2 + 10, 125, 49, 49);
            g.drawString("400 000 €", longueur2 + 10, 185);
            credit = 2;
        }
        if (verif(600000, lvl))
        {
            g.fillRect(longueur2 + 10, 225, 49, 49);
            g.drawString("600 000 €", longueur2 + 10, 285);
            credit = 3;
        }
    }
    //+++++++++++++++++++++++++++++++++++++++++FIN PARTIE EMPRUNT++++++++++++++++++++++++++++++++++++++++++++
    
    public static double interet (Graphics2D g,int x, double rembourser, int jouremprunt)
    {
        //Creation du montant à rembourser avec un taux à 3,8%
        rembourser = (0.038*x+x)/jouremprunt;
        g.setColor(Color.WHITE);
        g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 750, 30);
        g.setColor(Color.BLACK);
        Font font = new Font("Serif", Font.PLAIN, 20);
        g.setFont(font);
        g.drawString("Vous avez emprunte "+x+"€ et vous devez rembourser "+(0.038*x+x)+"€ soit "+rembourser+"€ par action", longueur2 / 5, hauteur2 / 2);
        font = new Font("Serif", Font.PLAIN, 11);
        g.setFont(font);
        actionpret = action;
        daypret = day;
        return rembourser;
    }
    
    //Remplissage du tableau 
    public static void tableau_exp ()
    {
        niveaux[0]=0;
        niveaux[1]=20;
        niveaux[2]=40;
        niveaux[3]=50;
        niveaux[4]=80;
        niveaux[5]=100;
        niveaux[6]=130;
        niveaux[7]=150;
        niveaux[8]=180;
        niveaux[9]=190;
        niveaux[10]=200;
        niveaux[11]=220;
        niveaux[12]=250;
        niveaux[13]=300;
        niveaux[14]=320;
        niveaux[15]=350;
        niveaux[16]=380;
        niveaux[17]=400;
        niveaux[18]=450;
        niveaux[19]=500;        
    }

    //Determination du niveau du joueur
    public static int exp ()
    {
        //Determination du niveau du joueur
        int niveau_provisoire=0;

        while (nbrhabitants>niveaux[niveau_provisoire])
        {
            niveau_provisoire++;
            if (niveau_provisoire>=20)
            {
                break;
            }
        }
        if (niveau_provisoire>lvl)
        {
            lvl=niveau_provisoire;
        }
        return lvl;
    }
    
    //Fonction qui verifie si le joueur a construit assez de centrales
    public static boolean verifelec (int i)
    {
        return industriels[2][1]>=i;
    }
    
    //Fonction qui verifie si le joueur a construit assez de stations
    public static boolean verifeau (int i)
    {
        return industriels[2][2]>=i;
    }

    //Fonction qui verifie si le joueur a construit assez d'ecoles
    public static boolean verifecole(int i)
    {
        return administration[2][2]>=i;
    }
    
    //Fonction qui verifie si le joueur a construit assez de gendarmeries
    public static boolean verifgendarmerie(int i)
    {
        return administration[2][3]>=i;
        
    }
    
    //Calcul de l'argent gagne avec les habitations
    public static double win_money_habit()
    {
        double moneytemp = 0;
        double money_habit = 0;
        for (int i=0; i<=3; i++)
        {
            moneytemp = habitations[1][i]*habitations[2][i];
            money_habit = moneytemp + money_habit;
        }
        return money_habit;
    }

    //Calcul de l'argent perdu avec les industries
    public static double lose_money_indus()
    {
        double moneytemp = 0;
        double money_indus = 0;
        for (int j=0; j<=2; j++)
        {
            moneytemp = industriels[1][j]*industriels[2][j];
            money_indus = moneytemp + money_indus;
        }
        double money_industrie = -money_indus;
        return money_industrie;
    }

    //Calcul de l'argent gagne avec les commerces
    public static double win_money_commerce()
    {
        double moneytemp = 0;
        double money_comm = 0;
        for (int i=0; i<=2; i++)
        {
            moneytemp = commerce[1][i]*commerce[2][i];
            money_comm = money_comm+moneytemp;
        }
        return money_comm;
    }
    
     //Calcul de l'argent gagne avec les loisirs
    public static double win_money_loisir()
    {
        double moneytemp;
        double money_lois = 0;
        for (int i=0;i<=2; i++)
        {
            moneytemp = loisirs[1][i]*loisirs[2][i];
            money_lois = money_lois + moneytemp;
        }
        return money_lois;
    }
    
    
    //Calcul de l'argent perdu avec les administrations
    public static double lose_money_admin()
    {
        double moneytemp;
        double money_adminis = 0;
        double money_admin;
        for (int i=0; i<=2; i++)
        {
            moneytemp = administration[1][i]*administration[2][i];
            money_adminis = money_adminis + moneytemp;
        }
        money_admin = -money_adminis;   //Passage en negatif car cela fait perdre de l'argent
        return money_admin;
    }
    
    public static int generate_number (int g, int h)    //Generation d'un nombre aleatoire
    {
        Random r = new Random();
        int nbr_home = g + r.nextInt(h - g);
        return nbr_home;
    }
    
    public static void properties(Graphics2D g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(490, 70, 350, 500);
        Font font = new Font("Serif", Font.PLAIN, 20);
        g.setFont(font);
        g.setColor(Color.BLACK);
        
        g.drawString("Vous possèdez "+(int)habitations[2][0]+" petites maisons", 500, 100);
        g.drawString("Vous possèdez "+(int)habitations[2][1]+" grandes maisons", 500, 120);
        g.drawString("Vous possèdez "+(int)habitations[2][2]+" villas", 500, 140);
        g.drawString("Vous possèdez "+(int)habitations[2][3]+" immeubles", 500, 160);

        g.drawString("Vous possèdez "+(int)industriels[2][0]+" usines", 500, 200);
        g.drawString("Vous possèdez "+(int)industriels[2][1]+" centrales à charbon", 500, 220);
        g.drawString("Vous possèdez "+(int)industriels[2][2]+" stations d'epuration", 500, 240);

        g.drawString("Vous possèdez "+(int)commerce[2][0]+" epiceries", 500, 300);
        g.drawString("Vous possèdez "+(int)commerce[2][1]+" marches", 500, 320);
        g.drawString("Vous possèdez "+(int)commerce[2][2]+" centre commerciaux", 500, 340);

        g.drawString("Vous possèdez "+(int)loisirs[2][0]+" zones d'activites", 500, 400);
        g.drawString("Vous possèdez "+(int)loisirs[2][1]+" salles de sport", 500, 420);
        g.drawString("Vous possèdez "+(int)loisirs[2][2]+" cinemas", 500, 440);

        g.drawString("Vous possèdez "+(int)administration[2][0]+" transports en commun", 500, 500);
        g.drawString("Vous possèdez "+(int)administration[2][1]+" parcs", 500, 520);
        g.drawString("Vous possèdez "+(int)administration[2][2]+" ecoles", 500, 540);
        g.drawString("Vous possèdez "+(int)administration[2][3]+" gendarmeries", 500, 560);
    }

    //Fonction qui calcule la pollution
    public static double calculpollution()
    {
        double pollution_finale;
        double pollhabit;
        double pollhabitfinale = 0;
        double pollindus;
        double pollindusfinale = 0;
        double pollloisirs;
        double pollloisirsfinale = 0;
        double polladmin;
        double polladminfinale = 0;
        for (int j=0; j<=3; j++)
        {
            pollhabit = habitations[2][j]*habitations[4][j];
            pollhabitfinale = pollhabitfinale + pollhabit;
        }

        for (int h=0; h<=2; h++)
        {
            pollindus = industriels[2][h]*industriels[4][h];
            pollindusfinale = pollindusfinale + pollindus;
        }
        for (int u=0; u<=2; u++)
        {
            pollloisirs = loisirs[2][u]*loisirs[5][u];
            pollloisirsfinale = pollloisirsfinale + pollloisirs;
        }
        for (int k=0; k<=2; k++)
        {
            polladmin = administration[4][k]*administration[2][k];
            polladminfinale = polladminfinale + polladmin;
        }
        pollution_finale = pollhabitfinale + pollindusfinale + pollloisirsfinale - polladminfinale;
        return pollution_finale;
    }
    
    /*

    +++++++++++++++++++++++++++++++Tableaux industrie++++++++++++++++++++++++++++++++++++

    */
    public static void industrie ()
    {
        //Case 0 cout a l'achat
        industriels[0][0]=20000;
        industriels[0][1]=60000;
        industriels[0][2]=50000;
        
        //Case 1 argent pedu par jour pour 1 item
        industriels[1][0]=500;
        industriels[1][1]=800;
        industriels[1][0]=300;
        
        //Case 3 diminution du bonheur
        industriels[3][0]=1;
        industriels[3][1]=1;
        industriels[3][2]=2;
    }

    public static void actuindustrie ()
    {
        int indus0 = generate_number(1,4);
        int indus1 = generate_number(3,5);
        int indus2 = generate_number(1,3);
        
        //Case 4 augmentation de la pollution par jour
        industriels[4][0]=0.5*indus0;
        industriels[4][1]=0.7*indus1;
        industriels[4][2]=0.5*indus2;
        
        //Case 5 augmentation de l'emploi
        industriels[5][0]=indus0;
        industriels[5][1]=indus1;
        industriels[5][2]=indus2;
    }

    /*
    +++++++++++++++++++++++++++++Tableaux habitations++++++++++++++++++++++++++++++
    */

    public static void home ()
    { //Remplissage des infos pour les habitations
        
        //Case 0 cout a l'achat
        habitations[0][0]=50000;
        habitations[0][1]=150000;
        habitations[0][2]=250000;
        habitations[0][3]=450000;
        
        //Case 3 augmentation du bonheur 
        habitations[3][0]=1;    
        habitations[3][1]=2;
        habitations[3][2]=2;
        habitations[3][3]=3;
}

    public static void actuhome(){
        int habit0 = generate_number(2,6);  // On change le nombre d'habitants de chaque objet
        int habit1 = generate_number(3,8); 
        int habit2 = generate_number(4,8);  //On genere les nombres d'habitants
        int habit3 = generate_number(20,51);
        
        //Case 1 argent gagne par jour pour 1 item
        habitations[1][0]=2000*habit0;
        habitations[1][1]=2000*habit1;
        habitations[1][2]=2000*habit2;
        habitations[1][3]=2000*habit3;
        
        //Case 4 augmentation de la pollution par jour pour 1 item
        habitations[4][0]=0.2*habit0;
        habitations[4][1]=0.2*habit1;  
        habitations[4][2]=0.2*habit2;
        habitations[4][3]=0.2*habit3;
        
        //Case 5 augmentation du nombre d'habitants pour 1 item construit
        habitations[5][0]=habit0;
        habitations[5][1]=habit1;
        habitations[5][2]=habit2;
        habitations[5][3]=habit3;
    }

    /*++++++++++++++++++++++++++++++++++++++Tableau commerce+++++++++++++++++++++++++++++++*/
    public static void commerces ()
    {
        //Case 0 cout a l'achat
        commerce[0][0]=60000;
        commerce[0][1]=500000;
        commerce[0][2]=600000;    
        
        //Case 1 argent gagne par jour pour 1 item
        commerce[1][0]=80*nbrhabitants;
        commerce[1][1]=120*nbrhabitants;
        commerce[1][2]=150*nbrhabitants;
        
        //Case 3 augmentation du bonheur par jour pour 1 item
        commerce[3][0]=1;
        commerce[3][1]=2;
        commerce[3][2]=2;
    }
    
    public static void actucommerce()
    {
        int comm1 = generate_number(2, 5);
        int comm2 = generate_number(8, 11);
        int comm3 = generate_number(10, 15);
        
        //Case 4 augmentation de l'emploi par jour
        commerce[4][0]=0.5*comm1;
        commerce[4][1]=0.3*comm2;
        commerce[4][2]=0.3*comm3;
    }
    
    /*+++++++++++++++++++++++++++++++Tableaux loisirs+++++++++++++++++++++++++++++++++++++++*/
    
    public static void loisir()
    {
        //Case 0 cout a l'achat
        loisirs[0][0]=25000;
        loisirs[0][1]=20000;
        loisirs[0][2]=30000;
        
        //Case 2 argent gagne par jour pour 1 item
        loisirs[1][0]=2500;
        loisirs[1][1]=1000;
        loisirs[1][2]=1200;
        
        //Case 3 augmentation du bonheur
        loisirs[3][0]=5;
        loisirs[3][1]=4;
        loisirs[3][2]=3;
    }
    public static void actuloisir ()
    {
        int lois=generate_number(1,4);  
        
        //Case 4 augmentation de l'emploi par jour
        loisirs[4][0]=lois*0.7;
        loisirs[4][1]=lois*0.6;
        loisirs[4][2]=lois*0.6;
        
        //Case 5 augmentation de la pollution par jour
        loisirs[5][0]=lois*0.9;
        loisirs[5][1]=lois*0.8;
        loisirs[5][2]=lois*0.5;
    }
    
    /*+++++++++++++++++++++++++++++++++++Tableau administration+++++++++++++++++++++++++++++++*/
    public static void admin ()
    {
        
        //Case 0 cout à l'achat
        administration[0][0]=200000;
        administration[0][1]=80000;
        administration[0][2]=150000;
        administration[0][3]=100000;
        
        //Case 3 Augmentation du bonheur
        administration[3][0]=4;
        administration[3][1]=6;
        administration[3][2]=5;
        administration[3][3]=2;
    }
    
    public static void actuadmin ()
    {
        int adm = generate_number(2,5);
        
        //Case 1 argent perdu par jour pour 1 item
        administration[1][0]=adm*350;
        administration[1][1]=adm*250;
        administration[1][2]=adm*150;
        administration[1][3]=adm*150;
        
        //Case 4 diminution de la pollution par jour pour 1 item
        administration[4][0]=adm*0.2;
        administration[4][1]=adm*0.4;
        administration[4][2]=0;        
        administration[4][3]=0;        
    }
    
    
    /*
    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    */
    
    //Partie de Evan
    
    //recuperation de la res²olution ecran
    static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    static int x1 = -50,y1 = -50, i = 0, option = 0, categories = 0, longueur  = (int)dimension.getWidth(), hauteur = (int)dimension.getHeight();
    static int batiment1 = 0, batiment2 = 0, batiment3 = 0, batiment4 = 0, batiment5 = 0;
    static int longueur2 = longueur / 50 * 50 - 50, hauteur2 = hauteur / 50 * 50 - 100;
    static BufferedImageOp bufop = null;
    static BufferedReader br; static BufferedWriter bw;
    static String avecsauv[][], sanssauv[][];
    static int taillex = 1, tailley = 1;
    static JFrame fenetre;
    Timer timer;
    static double montant_a_rembourser=0;
    static double montant_deja_rembourse = 0;
    static int jouremprunt = 0;
    static int up = 0, m = 0, actionpret = action, daypret = day;
    
    static boolean dette = false, emprunt = false, suitedette = false, fait = false, thunes = false, real = false, suite2dette = false, remb = false;
    
    @Override
   public void mouseClicked(MouseEvent e) 
    {
    }
    
    @Override
    public void mousePressed(MouseEvent e) 
    {
        x1 = e.getX();                          //coordonnees en x
        y1 = e.getY();                          //coordonnees en y
        
        repaint();
    }
    
    @Override
   public void mouseReleased(MouseEvent e) 
    {
    }
    
    @Override
   public void mouseEntered(MouseEvent e) 
    {
    }
    
    @Override
    public void mouseExited(MouseEvent e) 
    {
    }
    
    static void Image(BufferedImage picture,String nom,int x, int y)
    {
        fenetre = new JFrame(nom);
        JPanel panel = new JeuISNavant();                             // creation du panneau
        fenetre.setContentPane(panel);
        fenetre.pack();
        fenetre.setExtendedState(fenetre.MAXIMIZED_BOTH);       //afficher l'image en plein ecran
        fenetre.setLocationRelativeTo(null);
        fenetre.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt){
                int reponse = JOptionPane.showConfirmDialog(fenetre,
                        "Souhaitez-vous sauvegarder?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (reponse==JOptionPane.YES_OPTION){
                    try {
                        bw = new BufferedWriter (new FileWriter(new File("src/jeuisnavant/sauv" + hauteur + "_" + longueur + ".txt")));
                        for (int i = 0; i <= tailley; i++)
                        {
                            if (i == tailley)
                            {
                                bw.newLine();
                                //Ligne : argent
                                bw.write(String.valueOf(a));
                                bw.newLine();
                                //Ligne : bonheur
                                bw.write(String.valueOf(b));
                                bw.newLine();
                                //Ligne : emploi
                                bw.write(String.valueOf(e));
                                bw.newLine();
                                //Ligne : pollution
                                bw.write(String.valueOf(p));
                                bw.newLine();
                                //Ligne : nombre d'habitants
                                bw.write(String.valueOf(nbrhabitants));
                                bw.newLine();
                                //Ligne : jour actuel
                                bw.write(String.valueOf(day));
                                bw.newLine();
                                //Ligne : niveau
                                bw.write(String.valueOf(lvl));;
                                bw.newLine();
                                //Ligne : booleen upgradei
                                if (upgradei==true)
                                {
                                    bw.write("1");
                                }
                                else
                                {
                                    bw.write("0");
                                }
                                bw.newLine();
                                //Ligne : nombre d'action restant
                                bw.write(String.valueOf(action));
                                bw.newLine();
                                //Ligne : int passe (nombre de tour passes par le joueur)
                                bw.write(String.valueOf(passe));
                                bw.newLine();
                                //Ligne : categorie habitations : nombre de petites maisons
                                bw.write(String.valueOf(habitations[2][0]));
                                bw.newLine();
                                //Ligne : categorie habitations : nombre de grandes maisons
                                bw.write(String.valueOf(habitations[2][1]));
                                bw.newLine();
                                //Ligne : categorie habitations : nombre de villas
                                bw.write(String.valueOf(habitations[2][2]));
                                bw.newLine();
                                //Ligne : categorie habitations : nombre d'immeubles
                                bw.write(String.valueOf(habitations[2][3]));
                                bw.newLine();
                                //Ligne : categorie industries : nombre d'usines
                                bw.write(String.valueOf(industriels[2][0]));
                                bw.newLine();
                                //Ligne : categorie industries : nombre de centrales 
                                bw.write(String.valueOf(industriels[2][1]));
                                bw.newLine();
                                //Ligne : categorie industries : nombre de stations
                                bw.write(String.valueOf(industriels[2][2]));
                                bw.newLine();
                                //Ligne : categorie zones commerciales : nombre d'epiceries
                                bw.write(String.valueOf(commerce[2][0]));
                                bw.newLine();
                                //Ligne : categorie zones commerciales : nombre de zones d'activites
                                bw.write(String.valueOf(commerce[2][1]));
                                bw.newLine();
                                //Ligne : categorie zones commerciales : nombre de centre commerciaux
                                bw.write(String.valueOf(commerce[2][2]));
                                bw.newLine();
                                //Ligne : categorie loisirs : zone d'activite
                                bw.write(String.valueOf(loisirs[2][0]));
                                bw.newLine();
                                //Ligne : categorie loisirs : salle de sport
                                bw.write(String.valueOf(loisirs[2][1]));
                                bw.newLine();
                                //Ligne : categorie loisirs : cinema
                                bw.write(String.valueOf(loisirs[2][2]));
                                bw.newLine();
                                //Ligne : admin et gestion : transports en commun
                                bw.write(String.valueOf(administration[2][0]));
                                bw.newLine();
                                //Ligne : admin et gestion : parc
                                bw.write(String.valueOf(administration[2][1]));
                                bw.newLine();
                                //Ligne : admin et gestion : zones commerciales
                                bw.write(String.valueOf(administration[2][2]));
                                bw.newLine();
                                //Ligne : admin et gestion : gendarmerie
                                bw.write(String.valueOf(administration[2][3]));
                                bw.close();
                            } else {
                                for (int j = 0; j < taillex; j++)
                                {
                                    bw.write(avecsauv[j][i]);
                                    if ((i != tailley - 1) || (j != taillex - 1))
                                    {
                                        bw.newLine();
                                    }
                                }
                            }
                        }
                        fenetre.dispose();
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);
    }
    
    public static void main(String[] args) throws InterruptedException, IOException{
        
        BufferedImage picture = new BufferedImage(longueur,hauteur,BufferedImage.TYPE_INT_RGB);
        
        for (int j = 0; j < hauteur; j++)
        {
            for (int i = 0; i < longueur; i++)
            {
                picture.setRGB(i, j, 0x00FF00);
            }
        }
        Image(picture,"ISNCity",0,0);
    }
    
    //------------------------HABITATIONS---------------------------------------
    
    public void PetiteMaison(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage PM = ImageIO.read(new File("src/batiment/PM.png"));
        g.drawImage(PM, bufop, x, y);
        g.setColor(java.awt.Color.decode("#000000"));
        
        if ((x < longueur2) && (y < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment1 = 0;
        }
    }
    
    public void GrandeMaison(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage GM = ImageIO.read(new File("src/batiment/GM.png"));
        g.drawImage(GM, bufop, x, y);
        
        if ((x < longueur2) && (y < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment1 = 0;
        }
    }
    
    public void Villa(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage V = ImageIO.read(new File("src/batiment/V.png"));
        g.drawImage(V, bufop, x, y);
        
        if ((x < longueur2) && (y < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment1 = 0;
        }
    }
    
    public void Immeuble(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage I = ImageIO.read(new File("src/batiment/I.png"));
        g.drawImage(I, bufop, x, y);
        
        if ((x < longueur2) && (y < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment1 = 0;
        }
    }
    
    //------------------------INDUSTRIES----------------------------------------
    
    public void Usines(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage U = ImageIO.read(new File("src/batiment/U.png"));
        g.drawImage(U, bufop, x, y);
        
        if ((x < longueur2) && (y < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment2 = 0;
        }
    }
    
    public void Centraleacharbon(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage CAC = ImageIO.read(new File("src/batiment/CAC.png"));
        g.drawImage(CAC, bufop, x, y);
        
        if ((x < longueur2) && (y < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment2 = 0;
        }
    }
    
    public void Stationdepuration(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage SDE = ImageIO.read(new File("src/batiment/SDE.png"));
        g.drawImage(SDE, bufop, x, y);
        
        if ((x < longueur2) && (y < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment2 = 0;
        }
    }
    
    //------------------------COMMERCES-----------------------------------------
    
    public void Epicerie(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage E = ImageIO.read(new File("src/batiment/EP.png"));
        g.drawImage(E, bufop, x, y);
        
        if ((x < longueur2) && (y < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment3 = 0;
        }
    }
    
    public void Marche(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage M = ImageIO.read(new File("src/batiment/M.png"));
        g.drawImage(M, bufop, x1, y1);
        
        if ((x1 < longueur2) && (y1 < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment3 = 0;
        }
    }
    
    public void Centrecommercial(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage CC = ImageIO.read(new File("src/batiment/CC.png"));
        g.drawImage(CC, bufop, x, y);
        
        if ((x < longueur2) && (y < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment3 = 0;
        }
    }
    
    //------------------------LOISIRS-------------------------------------------
    
    public void Zonedactivite(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage ZA = ImageIO.read(new File("src/batiment/ZA.png"));
        g.drawImage(ZA, bufop, x1, y1);
        
        if ((x1 < longueur2) && (y1 < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment4 = 0;
        }
    }
    
    public void Salledesport(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage SDS = ImageIO.read(new File("src/batiment/SDS.png"));
        g.drawImage(SDS, bufop, x, y);
        
        if ((x < longueur2) && (y < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment4 = 0;
        }
    }
    
    public void Cinema(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage C = ImageIO.read(new File("src/batiment/C.png"));
        g.drawImage(C, bufop, x, y);
        
        if ((x < longueur2) && (y < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment4 = 0;
        }
    }
    
    //------------------------ADMINISTRATION------------------------------------
    
    public void Transports(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage T = ImageIO.read(new File("src/batiment/T.png"));
        g.drawImage(T, bufop, x, y);
        
        if ((x < longueur2) && (y < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment5 = 0;
        }
    }
    
    public void Parc(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage parc = ImageIO.read(new File("src/batiment/P.png"));
        g.drawImage(parc, bufop, x, y);
        
        if ((x < longueur2) && (y < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment5 = 0;
        }
    }
    
    public void Ecole(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage E = ImageIO.read(new File("src/batiment/EC.png"));
        g.drawImage(E, bufop, x, y);
        
        if ((x < longueur2) && (y < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment5 = 0;
        }
    }
    
    //gendarmerie
    public void Gendarmerie(Graphics2D g,int x, int y) throws IOException
    {
        BufferedImage G = ImageIO.read(new File("src/batiment/G.png"));
        g.drawImage(G, bufop, x, y);
        
        if ((x < longueur2) && (y < hauteur2)) //si clic sur la map
        {
            categories = 0;
            batiment5 = 0;
        }
    }
    
    //remplissage des tableaux
    static void tailletableau() throws IOException
    {
        //creation du  buffered
        br = new BufferedReader (new FileReader (new File ("src/jeuisnavant/sauv" + hauteur + "_" + longueur + ".txt")));
        String texte;
        taillex = longueur2/50;
        tailley = hauteur2/50;
        
        sanssauv = new String [taillex][tailley];
        avecsauv = new String [taillex][tailley];
        
        for (int i = 0; i < tailley; i++)
        {
            for (int j = 0; j < taillex; j++)
            {
                texte = br.readLine();
                avecsauv[j][i] = texte;
            }
        }
        //Ligne : argent
        texte = br.readLine();
        a=Double.parseDouble(texte);
        //Ligne : bonheur
        texte = br.readLine();
        b = Double.parseDouble(texte);
        //Ligne : emploi
        texte = br.readLine();
        e = Double.parseDouble(texte);
        //Ligne : pollution
        texte = br.readLine();
        p = Double.parseDouble(texte);
        //Ligne : nombre d'habitants
        texte = br.readLine();
        nbrhabitants = Double.parseDouble(texte);
        //Ligne : jour actuel
        texte = br.readLine();
        day = Integer.valueOf(texte);
        //Ligne : niveau
        texte = br.readLine();
        lvl = Integer.valueOf(texte);
        //Ligne : booleen upgradei
        texte = br.readLine();
        if (Integer.valueOf(texte)==1)
        {
            upgradei=true;
        }
        else
        {
            upgradei=false;
        }
        //Ligne : nombre restant d'actions
        texte = br.readLine();
        action = Integer.valueOf(texte);
        //ligne 10 : int passe (nombre de tours passes par le joueur)
        texte = br.readLine();
        passe = Integer.valueOf(texte);
        //Ligne 11 : categorie habitations : nombre de petites maisons
        texte = br.readLine();
        habitations[2][0] = Double.parseDouble(texte);
        //Ligne 12 : categorie habitations : nombre de grandes maisons
        texte = br.readLine();
        habitations[2][1] = Double.parseDouble(texte);
        //Ligne 13 : categorie habitations : nombre de villas
        texte = br.readLine();
        habitations[2][2] = Double.parseDouble(texte);
        //Ligne 14 :valeur = Read.readLine(); categorie habitations : nombre d'immeubles
        texte = br.readLine();
        habitations[2][3] = Double.parseDouble(texte);
        //Ligne 15 : categorie industries : nombre d'usines
        texte = br.readLine();
        industriels[2][0] = Double.parseDouble(texte);
        //Ligne 16 : categorie industries : nombre de centrales 
        texte = br.readLine();
        industriels[2][1] = Double.parseDouble(texte);
        //Ligne 17 : categorie industries : nombre de stations
        texte = br.readLine();
        industriels[2][2] = Double.parseDouble(texte);
        //Ligne 18 : categorie zones commerciales : nombre d'
        texte = br.readLine();
        commerce[2][0] = Double.parseDouble(texte);
        //Ligne 19 : categorie zones commerciales : nombre de zones d'activites
        texte = br.readLine();
        commerce[2][1] = Double.parseDouble(texte);
        //Ligne 20 : categorie zones commerciales : nombre de centre commerciaux
        texte = br.readLine();
        commerce[2][2] = Double.parseDouble(texte);
        //Ligne 21 : categorie loisirs : zone d'activite
        texte = br.readLine();
        loisirs[2][0] = Double.parseDouble(texte);
        //Ligne 22 : categorie loisirs : salle de sport
        texte = br.readLine();
        loisirs[2][1] = Double.parseDouble(texte);
        //Ligne 23 : categorie loisirs : cinema
        texte = br.readLine();
        loisirs[2][2] = Double.parseDouble(texte);
        //Ligne 24 : admin et gestion : transports en commun
        texte = br.readLine();
        administration[2][0] = Double.parseDouble(texte);
        //Ligne 25 : admin et gestion : parc
        texte = br.readLine();
        administration[2][1] = Double.parseDouble(texte);
        //Ligne 26 : admin et gestion : zones commerciales
        texte = br.readLine();
        administration[2][2] = Double.parseDouble(texte);
        //Ligne 27 : admin et gestion : gendarmerie
        texte = br.readLine();
        administration[2][3] = Double.parseDouble(texte);
        br.close();
    }
    
    public void creationfichier() throws IOException
    {
        bw = new BufferedWriter (new FileWriter(new File("src/jeuisnavant/sauv" + hauteur + "_" + longueur + ".txt")));
        
        int longg = 1, haut = 2, longmax = longueur2-200;
        while (longg <= longueur2 / 50)
        {
            bw.write("R");
            bw.newLine();
            longg++;
        }
        while (haut <= hauteur2 / 50)
        {
            bw.write("R");
            bw.newLine();
            bw.write("Culdesacd");
            bw.newLine();
            
            for (int k = 2; k < (longueur2/50)/2; k++)
            {
                bw.write("Routeh");
                bw.newLine();
            }
            
            if (haut == 2)
            {
                bw.write("Croisementb");
                bw.newLine();
                
            } else {
                bw.write("Croisement");
                bw.newLine();
            }
            
            for (int j = (longueur2 / 100)-1; j < longmax/50; j++)
            {
                bw.write("Routeh");
                bw.newLine();
            }
            
            bw.write("Culdesacg");
            bw.newLine();
            bw.write("R");
            bw.newLine();
            
            haut++;
            if (haut <= hauteur2 / 50)
            {
                for (int l = 0; l < (longueur2/50)/2; l++)
                {
                    bw.write("R");
                    bw.newLine();
                }
                
                bw.write("Routev");
                bw.newLine();
                
                for (int h = ((longueur2/50)/2)-1; h < longueur2/50; h++)
                {
                    bw.write("R");
                    bw.newLine();
                }
                
                haut++;
                if (haut <= hauteur2/50)
                {
                    for (int k = 2; k < (longueur2/50)/2; k++)
                    {
                        bw.write("R");
                        bw.newLine();
                    }

                    bw.write("Routev");
                    bw.newLine();

                    for (int h = ((longueur2/50)/2)+1; h < longueur2/50; h++)
                    {
                        bw.write("R");
                        bw.newLine();
                    }
                    haut++;
                }
            }
        }
        
        //Ligne : argent
        bw.write(String.valueOf(a));
        bw.newLine();
        //Ligne : bonheur
        bw.write(String.valueOf(b));
        bw.newLine();
        //Ligne : emploi
        bw.write(String.valueOf(e));
        bw.newLine();
        //Ligne : pollution
        bw.write(String.valueOf(p));
        bw.newLine();
        //Ligne : nombre d'habitants
        bw.write(String.valueOf(nbrhabitants));
        bw.newLine();
        //Ligne : jour actuel
        bw.write(String.valueOf(day));
        bw.newLine();
        //Ligne : niveau
        bw.write(String.valueOf(lvl));;
        bw.newLine();
        //Ligne : booleen upgradei
        if (upgradei==true)
        {
            bw.write("1");
        }
        else
        {
            bw.write("0");
        }
        bw.newLine();
        //Ligne : nombre d'action restant
        bw.write(String.valueOf(action));
        bw.newLine();
        //Ligne : int passe (nombre de tour passes par le joueur)
        bw.write(String.valueOf(passe));
        bw.newLine();
        //Ligne : categorie habitations : nombre de petites maisons
        bw.write(String.valueOf(habitations[2][0]));
        bw.newLine();
        //Ligne : categorie habitations : nombre de grandes maisons
        bw.write(String.valueOf(habitations[2][1]));
        bw.newLine();
        //Ligne : categorie habitations : nombre de villas
        bw.write(String.valueOf(habitations[2][2]));
        bw.newLine();
        //Ligne : categorie habitations : nombre d'immeubles
        bw.write(String.valueOf(habitations[2][3]));
        bw.newLine();
        //Ligne : categorie industries : nombre d'usines
        bw.write(String.valueOf(industriels[2][0]));
        bw.newLine();
        //Ligne : categorie industries : nombre de centrales 
        bw.write(String.valueOf(industriels[2][1]));
        bw.newLine();
        //Ligne : categorie industries : nombre de stations
        bw.write(String.valueOf(industriels[2][2]));
        bw.newLine();
        //Ligne : categorie zones commerciales : nombre d'epiceries
        bw.write(String.valueOf(commerce[2][0]));
        bw.newLine();
        //Ligne : categorie zones commerciales : nombre de zones d'activites
        bw.write(String.valueOf(commerce[2][1]));
        bw.newLine();
        //Ligne : categorie zones commerciales : nombre de centre commerciaux
        bw.write(String.valueOf(commerce[2][2]));
        bw.newLine();
        //Ligne : categorie loisirs : zone d'activite
        bw.write(String.valueOf(loisirs[2][0]));
        bw.newLine();
        //Ligne : categorie loisirs : salle de sport
        bw.write(String.valueOf(loisirs[2][1]));
        bw.newLine();
        //Ligne : categorie loisirs : cinema
        bw.write(String.valueOf(loisirs[2][2]));
        bw.newLine();
        //Ligne : admin et gestion : transports en commun
        bw.write(String.valueOf(administration[2][0]));
        bw.newLine();
        //Ligne : admin et gestion : parc
        bw.write(String.valueOf(administration[2][1]));
        bw.newLine();
        //Ligne : admin et gestion : zones commerciales
        bw.write(String.valueOf(administration[2][2]));
        bw.newLine();
        //Ligne : admin et gestion : gendarmerie
        bw.write(String.valueOf(administration[2][3]));
        bw.close();
    }
    
    public void thune(Graphics2D g)
    {
        g.setColor(java.awt.Color.decode("#000000"));
        g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur/3, hauteur/2);

        categories = 0;
        option = 0;
        batiment1 = 0;
        batiment2 = 0;
        batiment3 = 0;
        batiment4 = 0;
        batiment5 = 0;
        thunes = false;
    }
    
    public void verif(Graphics2D g) 
    {
        Font font = new Font("Serif", Font.PLAIN, 20);
        g.setFont(font);
        g.setColor(Color.BLACK);
        
        //PARTIE pollution
        adaptative_pollution = 0.10*p;
        b=b-adaptative_pollution;
        nbrhabitants=nbrhabitants-3;

        /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++CONDITIONS DES OBJETS CONSTRUITS++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
        //PARTIE eau
        if (!verifeau(lvl)) //Si les habitants n'ont pas accès à l'eau, le bonheur diminue
        {
            g.setColor(Color.WHITE);
            g.fillRect(495, 85, 300, 20);
            font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("Pas assez de stations !", 500, 100);
            b--;
            nbrhabitants=nbrhabitants-3;
        }

        //PARTIE electricite
        if (!verifelec(lvl))    //Si les habitants n'ont pas accès à l'electricite, le bonheur diminue
        {
            g.setColor(Color.WHITE);
            g.fillRect(495, 105, 300, 20);
            font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            b--;
            nbrhabitants=nbrhabitants-3;
            g.drawString("Pas assez de centrales !", 500, 120);
        }
        if (!verifecole(lvl))
        {
            g.setColor(Color.WHITE);
            g.fillRect(495, 125, 300, 20);
            font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            b--;
            nbrhabitants=nbrhabitants-3;
            g.drawString("Pas assez d'ecoles !", 500, 140);
        }
        if (!verifgendarmerie(lvl))
        {
            g.setColor(Color.WHITE);
            g.fillRect(495, 145, 300, 20);
            font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            b--;
            nbrhabitants=nbrhabitants-3;
            g.drawString("Pas assez de gendarmeries !", 500, 160);
        }
        //PARTIE emploi
        if (e<=lvl)
        {
            g.setColor(Color.WHITE);
            g.fillRect(495, 165, 300, 20);
            font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            a=a-10000;
            g.drawString("Le taux d'emploi est très bas, vous perdez de l'argent !", 500, 180);
        }
        //PARTIE bonheur
        if (b<=10*lvl)
        {
            g.setColor(Color.WHITE);
            g.fillRect(495, 185, 300, 20);
            font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            nbrhabitants=nbrhabitants-3;
            e = e - 3;
            g.drawString("Les habitants ne sont pas heureux ! Reglez cela au plus vite !", 500, 200);
        }
        fait = true;
    }
    
    public void argent(){
        double argent_gagne = win_money_loisir()+win_money_habit()+lose_money_indus()+win_money_commerce()+lose_money_admin();
        a=a + argent_gagne;
        remb = false;
    }
    
    public void reset() {
        batiment1 = 0;
        batiment2 = 0;
        batiment3 = 0;
        batiment4 = 0;
        batiment5 = 0;
        option = 0;
        categories = 0;
        dette = false;
    }
    
    public void remboursement(Graphics2D g){
        if (emprunt == true)
        {
            g.setColor(Color.WHITE);
            g.fillRect(15, 5, 200, 20);
            Font font = new Font("Serif", Font.PLAIN, 11);
            g.setFont(font);
            g.setColor(Color.BLACK);
            a=a-montant_a_rembourser;
            g.drawString("Vous remboursez aujourd'hui "+montant_a_rembourser+"€", 20, 20);
            montant_deja_rembourse = montant_deja_rembourse + montant_a_rembourser;

            if (montant_deja_rembourse>=jouremprunt*montant_a_rembourser)
            {
                g.setColor(Color.WHITE);
                g.fillRect(15, 20, 200, 20);
                g.setColor(Color.BLACK);
                g.drawString("Vous avez fini de rembourser votre prêt", 20, 35);
                emprunt=false;
            }
        }
    }
            
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        Font font = new Font("Serif", Font.PLAIN, 11);
        g.setFont(font);
        
        if (upgradei==false)
        {
            actuindustrie();                
        }
        actucommerce();
        actuloisir();
        actuadmin();
        //Partie Evan
        
        if (lvl>=20)
        {
            int reponse = JOptionPane.showConfirmDialog(fenetre,
                "Vous avez gagne ! Souhaitez-vous continuer?",
                "Victoire",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            if (reponse == JOptionPane.YES_OPTION){
            } else {
                //creationfichier
                System.exit(0);
            }
        }
        
        //Remplissage des tableaux
        home();
        industrie();
        commerces();
        loisir();
        admin();
        tableau_exp();
        
        while (i < 1)
        {
            i++;
            //recherche si le fichier existe
            File f = new File("src/jeuisnavant/sauv" + hauteur + "_" + longueur + ".txt");
            
            if(f.isFile())
            {                
                int reponse = JOptionPane.showConfirmDialog(fenetre,
                        "Souhaitez-vous continuer?",
                        "Partie",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (reponse == JOptionPane.YES_OPTION){
                } else {
                    try {
                        creationfichier();
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //si le fichier n'existe pas
            } else {
                try {
                    creationfichier();
                } catch (IOException ex) {
                    Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            try {
                tailletableau();
            } catch (IOException ex) {
                Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //refresh map
        for (int h = 0; h < tailley*50; h = h + 50)
        {
            for (int j = 0; j < taillex*50; j = j + 50)
            {
                try {
                    g.drawImage(ImageIO.read(new File("src/batiment/" + avecsauv[j/50][h/50] + ".png")), bufop, j, h);
                } catch (IOException ex) {
                    Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        //CONTRAINTES DU JEU (proportionelles au niveau du joueur)
        lvl = exp(); //Determination du niveau du joueur
        if ((categories == 0) && (option == 0) && (categories == 0) && (batiment1 == 0) && (batiment2 == 0) && (batiment3 == 0) && (batiment4 == 0) && (batiment5 == 0) && (fait == false)) //Au bout du jour 3, les contraintes rentrent en place
        {
            if (day == 3)
            {
                verif(g);
            }
            if (((actionpret > action) || ((actionpret <= action) && (daypret < day))) && (remb == false))
            {
                remboursement(g);
                remb = true;
            }
            comparison(ac, bc, ec, ptotalec, nbrhabitantsc, g);
            ac=a;
            bc=b;
            ec=e;
            ptotalec=ptotale;
            nbrhabitantsc=nbrhabitants;
        }
        
        if ((longueur2 + 10 <= x1) && (x1 <= longueur2 + 60) && (25 <= y1) && (y1 <= 75))               //si menu construction et clic sur parc
        {
            if (dette == true)
            {
                jouremprunt = 5;
                dette = false;
                suitedette = true;
            }
            //credit 1 2 ou 3
            else if ((credit == 1) || (credit == 2) || (credit == 3))
            {
                m = 200000;
                a = a + m;
                option = 0;
                credit = 0;
            }
            //si clic sur menu construction
            else if ((categories == 0) && (option == 0))
            {
                option = 1;
                
                //si clic sur la 1ère categorie
            } else if ((categories == 0) && (option == 1))
            {
                option = 0;
                categories = 1;
                
                //si clic sur le 1er batiment de la categorie 1
            } else if (categories == 1)
            {
                categories = 0;
                batiment1 = 1;
                batiment2 = 0;
                batiment3 = 0;
                batiment4 = 0;
                batiment5 = 0;
                
                //si clic sur le 1er batiment de la categorie 2
            } else if (categories == 2)
            {
                categories = 0;
                batiment1 = 0;
                batiment2 = 1;
                batiment3 = 0;
                batiment4 = 0;
                batiment5 = 0;
                
                //si clic sur le 1er batiment de la categorie 3
            } else if (categories == 3)
            {
                categories = 0;
                batiment1 = 0;
                batiment2 = 0;
                batiment3 = 1;
                batiment4 = 0;
                batiment5 = 0;
                
                //si clic sur le 1er batiment de la categorie 4
            } else if (categories == 4)
            {
                categories = 0;
                batiment1 = 0;
                batiment2 = 0;
                batiment3 = 0;
                batiment4 = 1;
                batiment5 = 0;
                
                //si clic sur le 1er batiment de la categorie 5
            } else if (categories == 5)
            {
                categories = 0;
                batiment1 = 0;
                batiment2 = 0;
                batiment3 = 0;
                batiment4 = 0;
                batiment5 = 1;
                
                
            } else {
                reset();
            }
            
        } else if ((longueur2 + 10 <= x1) && (x1 <= longueur2 + 60) && (125 <= y1) && (y1 <= 175))      //si menu construction et clic sur croisement
        {
            if (dette == true)
            {
                jouremprunt = 6;
                dette = false;
                suitedette = true;
            }
            
            //si 400 000
            else if ((credit == 2) || (credit == 3))
            {
                m = 400000;
                a = a + m;
                option = 0;
                credit = 0;
            }
            //si clic sur passer
            else if ((categories == 0) && (option == 0) && (passe < 1))
            {
                action--;
                fait = false;
                argent();
                passe++;
                
            }//si clic sur la 2ième categorie
            else if ((categories == 0) && (option == 1))
            {
                option = 0;
                categories = 2;
                
                //si clic sur le 2ième batiment de la categorie 1
            } else if (categories == 1)
            {
                categories = 0;
                batiment1 = 2;
                batiment2 = 0;
                batiment3 = 0;
                batiment4 = 0;
                batiment5 = 0;
                
                //si clic sur le 2ième batiment de la categorie 2
            } else if (categories == 2)
            {
                categories = 0;
                batiment1 = 0;
                batiment2 = 2;
                batiment3 = 0;
                batiment4 = 0;
                batiment5 = 0;
                
                //si clic sur le 2ième batiment de la categorie 3
            } else if (categories == 3)
            {
                categories = 0;
                batiment1 = 0;
                batiment2 = 0;
                batiment3 = 2;
                batiment4 = 0;
                batiment5 = 0;
                
                //si clic sur le 2ième batiment de la categorie 4
            } else if (categories == 4)
            {
                categories = 0;
                batiment1 = 0;
                batiment2 = 0;
                batiment3 = 0;
                batiment4 = 2;
                batiment5 = 0;
                
                //si clic sur le 2ième batiment de la categorie 5
            } else if (categories == 5)
            {
                categories = 0;
                batiment1 = 0;
                batiment2 = 0;
                batiment3 = 0;
                batiment4 = 0;
                batiment5 = 2;
                
                
            } else if ((option == 0) && (passe >= 1)) {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 15, 235, 20);
                g.setColor(Color.BLACK);
                g.drawString("Vous ne pouvez pas passer plus d'un tour par jour", longueur2/5, hauteur2/2);
                
                //si clic sans faire exprès
            } else {
                reset();
            }
            
        }else if ((longueur2 + 10 <= x1) && (x1 <= longueur2 + 60) && (225 <= y1) && (y1 <= 275))      //si menu construction et clic sur croisement
        {
            if (dette == true)
            {
                jouremprunt = 7;
                dette = false;
                suitedette = true;
            }
            //si credit
            else if ((categories == 0) && (option == 0) && (emprunt == false))
            {
                option = 3;
                
                //si emprunt en cours
            } else if ((categories == 0) && (option == 0) && (emprunt == true))
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 300, 25);
                g.setColor(Color.BLACK);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.drawString("Vous avez dejà un emprunt en cours", longueur2/5, hauteur2/2);
            }
            
            else if (credit == 3)
            {
                m = 600000;
                a = a + m;
                credit = 0;
                option = 0;
            }
            //si clic sur la 3ième categorie
            else if ((categories == 0) && (option == 1) && (batiment1 == 0) && (batiment2 == 0) && (batiment3 == 0) && (batiment4 == 0) && (batiment5 == 0))
            {
                option = 0;
                categories = 3;
                
                //si clic sur le 3ième batiment de la categorie 1
            } else if (categories == 1)
            {
                categories = 0;
                batiment1 = 3;
                batiment2 = 0;
                batiment3 = 0;
                batiment4 = 0;
                batiment5 = 0;
                
                //si clic sur le 3ième batiment de la categorie 2
            } else if (categories == 2)
            {
                categories = 0;
                batiment1 = 0;
                batiment2 = 3;
                batiment3 = 0;
                batiment4 = 0;
                batiment5 = 0;
                
                //si clic sur le 3ième batiment de la categorie 3
            } else if (categories == 3)
            {
                categories = 0;
                batiment1 = 0;
                batiment2 = 0;
                batiment3 = 3;
                batiment4 = 0;
                batiment5 = 0;
                
                //si clic sur le 3ième batiment de la categorie 4
            } else if (categories == 4)
            {
                categories = 0;
                batiment1 = 0;
                batiment2 = 0;
                batiment3 = 0;
                batiment4 = 3;
                batiment5 = 0;
                
                //si clic sur le 3ième batiment de la categorie 5
            } else if (categories == 5)
            {
                categories = 0;
                batiment1 = 0;
                batiment2 = 0;
                batiment3 = 0;
                batiment4 = 0;
                batiment5 = 3;
            } else {
                reset();
            }
        
        } else if ((longueur2 + 10 <= x1) && (x1 <= longueur2 + 60) && (325 <= y1) && (y1 <= 375))      //si menu construction et clic sur croisement
        {
            if (dette == true)
            {
                jouremprunt = 8;
                dette = false;
                suitedette = true;
            }
            
            //si clic sur l'option 4
            else if ((categories == 0) && (option == 0))
            {
                properties(g);
            } else 
                
            //si clic sur la 4ième categorie
            if ((categories == 0) && (option == 1) && (batiment1 == 0) && (batiment2 == 0) && (batiment3 == 0) && (batiment4 == 0) && (batiment5 == 0))
            {
                option = 0;
                categories = 4;
                
                //si clic sur le 4ième batiment de la categorie 1
            } else if (categories == 1)
            {
                batiment1 = 4;
                batiment2 = 0;
                batiment3 = 0;
                batiment4 = 0;
                batiment5 = 0;
                
                //si clic sur le 2ième batiment de la categorie 5
            } else if ((categories == 5) && (batiment2 == 0))
            {
                categories = 0;
                batiment5 = 4;
            } else {
                reset();
            }
            
            //si clic sur la 5ème partie de la structure
        } else if ((longueur2 + 10 <= x1) && (x1 <= longueur2 + 60) && (425 <= y1) && (y1 <= 475))
        {
            //si credit
            if (dette == true)
            {
                jouremprunt = 9;
                dette = false;
                suitedette = true;
            }
            //si clic sur la 5ième categorie
            else if ((categories == 0) && (option == 1) && (batiment1 == 0) && (batiment2 == 0) && (batiment3 == 0) && (batiment4 == 0) && (batiment5 == 0))
            {
                option = 0;
                categories = 5;
                
                //si 5ième option
            } else if ((categories == 0) && (option == 0) && (batiment1 == 0) && (batiment2 == 0) && (batiment3 == 0) && (batiment4 == 0) && (batiment5 == 0))
            {
                option = 5;
            } else {
                reset();
            }
            
        }else if ((x1 < longueur2) && (y1 < hauteur2))
            //converti pour arriver dans un angle d'un quadrillage non existant de carre de cote 50
        {
            x1 = x1/50;
            x1 = x1*50;
            y1 = y1/50;
            y1 = y1*50;
            option = 0;
        } else {
            reset();
        }
        
        font = new Font("Serif", Font.PLAIN, 11);
        g.setFont(font);
        
        if (suite2dette == true)
        {
            action--;
            argent();
            fait = false;
            option = 0;
            categories = 0;
            dette = false;
            emprunt = true;
            montant_a_rembourser = interet(g, m, 0, jouremprunt);
            suite2dette = false;
        }
        if (option == 5)
        {
            if ((real == false) && (a >= 500000))
            {
                a = a-500000;
                upgrades_indus();
                upgradei=true;
                action--;
                argent();
                fait = false;
                option = 0;
                real = true;
                
            } else if ((real == false) && (a < 500000)){
                option = 0;
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 25, 235, 20);
                g.setColor(Color.BLACK);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.drawString("Vous n'avez pas assez d'argent", longueur2/5, hauteur2/2);
                
            } else {
                option = 0;
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 15, 245, 20);
                g.setColor(Color.BLACK);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.drawString("Ameliorations dejà realisees", longueur2 / 5, hauteur2 / 2);
            }
        }
        else if (suitedette == true)
        {
            emprunt(g);
            suitedette = false;
            suite2dette = true;
        } else
        if ((option == 0) && (categories == 0) && (batiment1 == 0) && (batiment2 == 0) && (batiment3 == 0) && (batiment4 == 0) && (batiment5 == 0))
        { // menu de base
            actuhome();
            actuindustrie();
            actucommerce();
            
            //remise à 0 de l'affichage
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(longueur2, 0, longueur-longueur2, hauteur);
            
            g.setColor(java.awt.Color.BLACK);
            try {
                //1-construction
                g.drawImage(ImageIO.read(new File("src/batiment/CO.png")),bufop, longueur2 + 10, 25);
                g.drawString("Construction", longueur2 + 5, 90);
            
                //2-passer son tour
                g.drawImage(ImageIO.read(new File("src/batiment/PT.png")),bufop, longueur2 + 10, 125);
                g.drawString("Passer son", longueur2 + 10, 190);
                g.drawString("tour", longueur2 + 20, 200);
            
                //3-Credit
                g.drawImage(ImageIO.read(new File("src/batiment/CR.png")),bufop, longueur2 + 10, 225);
                g.drawString("Credit", longueur2 + 10, 290);
            
                //4-Propriete
                g.drawImage(ImageIO.read(new File("src/batiment/PROP.png")),bufop, longueur2 + 10, 325);
                g.drawString("Proprietes", longueur2 + 10, 390);
                
                //5-Ameliorer
                g.drawImage(ImageIO.read(new File("src/batiment/UP.png")),bufop, longueur2 + 10, 425);
                g.drawString("Ameliorer les", longueur2 + 5, 490);
                g.drawString("industries", longueur2 + 15, 500);
            } catch (IOException ex) {
                Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //si credit
        } else if (option == 3)
        {
            dette = true;
            option = 0;
            
            //remise à 0 de l'affichage
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(longueur2, 0, longueur-longueur2, hauteur);
            
            g.setColor(java.awt.Color.decode("#000000"));
            
            g.fillRect(longueur2 + 10, 25, 49, 49);
            g.drawString("Sur 5 jours", longueur2 + 10, 85);
            
            g.fillRect(longueur2 + 10, 125, 49, 49);
            g.drawString("Sur 6 jours", longueur2 + 10, 185);
            
            g.fillRect(longueur2 + 10, 225, 49, 49);
            g.drawString("Sur 7 jours", longueur2 + 10, 285);
            
            g.fillRect(longueur2 + 10, 325, 49, 49);
            g.drawString("Sur 8 jours", longueur2 + 10, 385);
            
            g.fillRect(longueur2 + 10, 425, 49, 49);
            g.drawString("Sur 9 jours", longueur2 + 10, 485);
            
            //si construction
        } else if ((option == 1) && (categories == 0) && (batiment1 == 0) && (batiment2 == 0) && (batiment3 == 0) && (batiment4 == 0) && (batiment5 == 0)) //construction
        { 
            //remise à 0 de l'affichage
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(longueur2, 0, longueur-longueur2, hauteur);
            
            g.setColor(Color.BLACK);
            try {
                //Habitations
                g.drawImage(ImageIO.read(new File("src/batiment/HAB.png")),bufop, longueur2 + 10, 25);
                g.drawString("Habitations", longueur2 + 10, 90);
            
                //Industries
                g.drawImage(ImageIO.read(new File("src/batiment/INDUS.png")),bufop, longueur2 + 10, 125);
                g.drawString("Industries", longueur2 + 10, 190);
            
                //Commerces
                g.drawImage(ImageIO.read(new File("src/batiment/COM.png")),bufop, longueur2 + 10, 225);
                g.drawString("Commerces", longueur2 + 10, 290);
            
                //Loisirs
                g.drawImage(ImageIO.read(new File("src/batiment/LOI.png")),bufop, longueur2 + 10, 325);
                g.drawString("Loisirs", longueur2 + 10, 390);
            
                //Administration
                g.drawImage(ImageIO.read(new File("src/batiment/ADM.png")),bufop, longueur2 + 10, 425);
                g.drawString("Administration", longueur2, 490);
            } catch (IOException ex) {
                Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //si habitation
        } else if ((categories == 1) && (batiment1 == 0) && (batiment2 == 0) && (batiment3 == 0) && (batiment4 == 0) && (batiment5 == 0))                        //si parc
        {
            //remise à 0 de l'affichage
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(longueur2, 0, longueur-longueur2, hauteur);
            
            g.setColor(java.awt.Color.decode("#000000"));
            
            try {
                //Petite Maison
                PetiteMaison(g, longueur2 + 10, 25);
                g.drawString("Petite", longueur2 + 10, 90);
                g.drawString("maison", longueur2 + 10, 100);
                g.drawString("50 000 €", longueur2 + 10, 110);
                
                //Grande Maison
                GrandeMaison(g, longueur2 + 10, 125);
                g.drawString("Grande", longueur2 + 10, 190);
                g.drawString("maison", longueur2 + 10, 200);
                g.drawString("150 000 €", longueur2 + 10, 210);
                
                //Villa
                Villa(g, longueur2 + 10, 225);
                g.drawString("Villa", longueur2 + 10, 290);
                g.drawString("250 000 €", longueur2 + 10, 300);
                
                //Immeuble
                Immeuble(g, longueur2 + 10, 325);
                g.drawString("Immeuble", longueur2 + 10, 390);
                g.drawString("450 000 €", longueur2 + 10, 400);
                
            } catch (IOException ex) {
                Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.setColor(Color.BLACK);
                
            //si industrie
        }else if ((categories == 2) && (batiment1 == 0) && (batiment2 == 0) && (batiment3 == 0) && (batiment4 == 0) && (batiment5 == 0))
        {
            //remise à 0 de l'affichage
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(longueur2, 0, longueur-longueur2, hauteur);
            
            g.setColor(java.awt.Color.decode("#000000"));
            
            try {
                //Usines
                Usines(g, longueur2 + 10, 25);
                g.drawString("Usine", longueur2 + 10, 90);
                g.drawString("20 000 €", longueur2 + 10, 100);
                
                //Centrale à charbon
                Centraleacharbon(g, longueur2 + 10, 125);
                g.drawString("Centrale à", longueur2 + 10, 190);
                g.drawString("charbon", longueur2 + 10, 200);
                g.drawString("60 000 €", longueur2 + 10, 210);
                
                //Station d'epuration
                Stationdepuration(g, longueur2 + 10, 225);
                g.drawString("Station", longueur2 + 10, 290);
                g.drawString("d'epuration", longueur2 + 10, 300);
                g.drawString("50 000 €", longueur2 + 10, 310);
                
            } catch (IOException ex) {
                Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.setColor(Color.BLACK);
            
            //si commerce
        } else if ((categories == 3) && (batiment1 == 0) && (batiment2 == 0) && (batiment3 == 0) && (batiment4 == 0) && (batiment5 == 0))
        {
            //remise à 0 de l'affichage
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(longueur2, 0, longueur-longueur2, hauteur);
            
            g.setColor(java.awt.Color.decode("#000000"));
            
            try {
                //Epicerie
                Epicerie(g, longueur2 + 10, 25);
                g.drawString("Epicerie", longueur2 + 10, 90);
                g.drawString("60 000 €", longueur2 + 10, 100);
                
                //Zone commerciale
                Marche(g, longueur2 + 10, 125);
                g.drawString("Marche", longueur2 + 10, 190);
                g.drawString("500 000 €", longueur2 + 10, 200);
                
                //Centre commercial
                Centrecommercial(g, longueur2 + 10, 225);
                g.drawString("Centre", longueur2 + 10, 290);
                g.drawString("commercial", longueur2 + 10, 300);
                g.drawString("600 000 €", longueur2 + 10, 310);
                
            } catch (IOException ex) {
                Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.setColor(Color.BLACK);
            
            //si loisirs
        } else if ((categories == 4) && (batiment1 == 0) && (batiment2 == 0) && (batiment3 == 0) && (batiment4 == 0) && (batiment5 == 0))
        {
            //remise à 0 de l'affichage
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(longueur2, 0, longueur-longueur2, hauteur);
            
            g.setColor(java.awt.Color.decode("#000000"));
            
            try {
                //Zone d'activite
                Zonedactivite(g, longueur2 + 10, 25);
                g.drawString("Zone", longueur2 + 10, 90);
                g.drawString("d'activites", longueur2 + 10, 100);
                g.drawString("25 000 €", longueur2 + 10, 110);
                
                //Salle de sport
                Salledesport(g, longueur2 + 10, 125);
                g.drawString("Salle de", longueur2 + 10, 190);
                g.drawString("sport", longueur2 + 10, 200);
                g.drawString("20 000 €", longueur2 + 10, 210);
                
                //Cinema
                Cinema(g, longueur2 + 10, 225);
                g.drawString("Cinema", longueur2 + 10, 290);
                g.drawString("30 000 €", longueur2 + 10, 300);
                
            } catch (IOException ex) {
                Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.setColor(Color.BLACK);
            
            //si administration
        } else if ((categories == 5) && (batiment1 == 0) && (batiment2 == 0) && (batiment3 == 0) && (batiment4 == 0) && (batiment5 == 0))
        {
            //remise à 0 de l'affichage
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(longueur2, 0, longueur-longueur2, hauteur);
            
            g.setColor(java.awt.Color.decode("#000000"));
            
            try {
                //Transports
                Transports(g, longueur2 + 10, 25);
                g.drawString("Transport", longueur2 + 10, 90);
                g.drawString("200 000 €", longueur2 + 10, 100);
                
                //Parc
                Parc(g, longueur2 + 10, 125);
                g.drawString("Parc", longueur2 + 10, 190);
                g.drawString("80 000 €", longueur2 + 10, 200);
                
                //Ecole
                Ecole(g, longueur2 + 10, 225);
                g.drawString("Ecole", longueur2 + 10, 290);
                g.drawString("150 000 €", longueur2 + 10, 300);
                
                //Gendarmerie
                Gendarmerie(g, longueur2 + 10, 325);
                g.drawString("Gendarmerie", longueur2 + 10, 390);
                g.drawString("100 000 €", longueur2 + 10, 400);
                
            } catch (IOException ex) {
                Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.setColor(Color.BLACK);
            //si batiment1 = 1
        } else if (batiment1 == 1)
        {
            int curseurh = batiment1-1;
            if (a<habitations[0][curseurh])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment1 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            { 
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-habitations[0][curseurh]; //Actualisation de l'argent pour la construction
                    b=b+habitations[3][curseurh]; //Actualisation du bonheur
                    nbrhabitants=nbrhabitants+habitations[5][curseurh];
                    habitations[2][curseurh]++;
                    p = calculpollution();
                    ptotale = ptotale + p;
                    action--;
                    argent();
                    fait = false;
                    
                    try {
                        PetiteMaison(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    avecsauv[x1/50][y1/50] = "PM";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment1 = 0;
                }
            }
            
            //si batiment1 = 2
        } else if (batiment1 == 2)
        {
            int curseurh = batiment1-1;
            if (a<habitations[0][curseurh])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment1 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-habitations[0][curseurh]; //Actualisation de l'argent pour la construction
                    b=b+habitations[3][curseurh]; //Actualisation du bonheur
                    nbrhabitants=nbrhabitants+habitations[5][curseurh];
                    habitations[2][curseurh]++;
                    p = calculpollution();
                    ptotale = ptotale + p;
                    action--;
                    argent();
                    fait = false;

                    try {
                        GrandeMaison(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    avecsauv[x1/50][y1/50] = "GM";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment1 = 0;
                }
            }
            
            //si batiment1 = 3
        } else if (batiment1 == 3)
        {
            int curseurh = batiment1-1;
            if (a<habitations[0][curseurh])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment1 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-habitations[0][curseurh]; //Actualisation de l'argent pour la construction
                    b=b+habitations[3][curseurh]; //Actualisation du bonheur
                    nbrhabitants=nbrhabitants+habitations[5][curseurh];
                    habitations[2][curseurh]++;
                    p = calculpollution();
                    ptotale = ptotale + p;
                    action--;
                    argent();
                    fait = false;

                    try {
                        Villa(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    avecsauv[x1/50][y1/50] = "V";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment1 = 0;
                }
            }
            
            //si batiment1 = 4
        } else if (batiment1 == 4)
        {
            int curseurh = batiment1-1;
            if (a<habitations[0][curseurh])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment1 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-habitations[0][curseurh]; //Actualisation de l'argent pour la construction
                    b=b+habitations[3][curseurh]; //Actualisation du bonheur
                    nbrhabitants=nbrhabitants+habitations[5][curseurh];
                    habitations[2][curseurh]++;
                    p = calculpollution();
                    ptotale = ptotale + p;
                    action--;
                    argent();
                    fait = false;

                    try {
                        Immeuble(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    avecsauv[x1/50][y1/50] = "I";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment1 = 0;
                }
            }
            
            //si batiment2 = 1
        } else if (batiment2 == 1)
        {
            int curseuri = batiment2-1;
            if (a<industriels[0][curseuri])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment2 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-industriels[0][curseuri];
                    b=b+industriels[3][curseuri];
                    e=e+industriels[5][curseuri];
                    industriels[2][curseuri]++;
                    p = calculpollution();
                    ptotale = ptotale + p;
                    action--;
                    argent();
                    fait = false;

                    try {
                        Usines(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    avecsauv[x1/50][y1/50] = "U";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment2 = 0;
                }
            }
            
            //si batiment2 = 2
        } else if (batiment2 == 2)
        {
            int curseuri = batiment2-1;
            if (a<industriels[0][curseuri])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment2 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-industriels[0][curseuri];
                    b=b+industriels[3][curseuri];
                    e=e+industriels[5][curseuri];
                    industriels[2][curseuri]++;
                    p = calculpollution();
                    ptotale = ptotale + p;
                    action--;
                    argent();
                    fait = false;

                    try {
                        Centraleacharbon(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    avecsauv[x1/50][y1/50] = "CAC";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment2 = 0;
                }
            }
            
            //si batiment2 = 3
        } else if (batiment2 == 3)
        {
            int curseuri = batiment2-1;
            if (a<industriels[0][curseuri])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment2 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-industriels[0][curseuri];
                    b=b+industriels[3][curseuri];
                    e=e+industriels[5][curseuri];
                    industriels[2][curseuri]++;
                    p = calculpollution();
                    ptotale = ptotale + p;
                    action--;
                    argent();
                    fait = false;
                
                    try {
                        Stationdepuration(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    avecsauv[x1/50][y1/50] = "SDE";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment3 = 0;
                }
            }
            
            //si batiment3 = 1
        } else if (batiment3 == 1)
        {
            int curseurc = batiment3-1;
            if (a<commerce[0][curseurc])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment3 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-commerce[0][curseurc];
                    b=b+commerce[3][curseurc];
                    e=e+commerce[4][curseurc];
                    commerce[2][curseurc]++;
                    p = calculpollution();
                    ptotale = ptotale + p;
                    action--;
                    argent();
                    fait = false;

                    try {
                        Epicerie(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    avecsauv[x1/50][y1/50] = "EP";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment3 = 0;
                }
            }
            
            //si batiment3 = 2
        } else if (batiment3 == 2)
        {
            int curseurc = batiment3-1;
            if (a<commerce[0][curseurc])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment3 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-commerce[0][curseurc];
                    b=b+commerce[3][curseurc];
                    e=e+commerce[4][curseurc];
                    commerce[2][curseurc]++;
                    p = calculpollution();
                    ptotale = ptotale + p;
                    action--;
                    argent();
                    fait = false;

                    try {
                        Marche(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    avecsauv[x1/50][y1/50] = "M";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment3 = 0;
                }
            }
            
            //si batiment3 = 3
        } else if (batiment3 == 3)
        {
            int curseurc = batiment3-1;
            if (a<commerce[0][curseurc])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment3 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-commerce[0][curseurc];
                    b=b+commerce[3][curseurc];
                    e=e+commerce[4][curseurc];
                    commerce[2][curseurc]++;
                    p = calculpollution();
                    ptotale = ptotale + p;
                    action--;
                    argent();
                    fait = false;

                    try {
                        Centrecommercial(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    avecsauv[x1/50][y1/50] = "CC";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment3 = 0;
                }
            }
            
            //si batiment4 = 1
        } else if (batiment4 == 1)
        {
            int curseurl = batiment4-1;
            if (a<loisirs[0][curseurl])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment4 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-loisirs[0][curseurl];
                    b=b+loisirs[3][curseurl];
                    e=e+loisirs[4][curseurl];
                    loisirs[2][curseurl]++;
                    action--;
                    argent();
                    
                    try {
                        Zonedactivite(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    avecsauv[x1/50][y1/50] = "ZA";
                    repaint();
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment4 = 0;
                }
            }
            
            //si batiment4 = 2
        } else if (batiment4 == 2)
        {
            int curseurl = batiment4-1;
            if (a<loisirs[0][curseurl])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment4 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-loisirs[0][curseurl];
                    b=b+loisirs[3][curseurl];
                    e=e+loisirs[4][curseurl];
                    loisirs[2][curseurl]++;
                    action--;
                    argent();
                    
                    try {
                        Salledesport(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    avecsauv[x1/50][y1/50] = "SDS";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment4 = 0;
                }
            }
            
            //si batiment4 = 3
        } else if (batiment4 == 3)
        {
            int curseurl = batiment4-1;
            if (a<loisirs[0][curseurl])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment4 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-loisirs[0][curseurl];
                    b=b+loisirs[3][curseurl];
                    e=e+loisirs[4][curseurl];
                    loisirs[2][curseurl]++;
                    action--;
                    argent();
                    
                    try {
                        Cinema(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    avecsauv[x1/50][y1/50] = "C";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment4 = 0;
                }
            }
            
            //si batiment5 = 1
        } else if (batiment5 == 1)
        {
            int curseura = batiment5-1;
            if (a<administration[0][curseura])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment5 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-administration[0][curseura];
                    b=b+administration[3][curseura];
                    administration[2][curseura]++;
                    action--;
                    argent();
                    
                    try {
                        Transports(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    avecsauv[x1/50][y1/50] = "T";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment5 = 0;
                }
            }
            
            //si batiment5 = 2
        } else if (batiment5 == 2)
        {
            int curseura = batiment5-1;
            if (a<administration[0][curseura])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment5 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-administration[0][curseura];
                    b=b+administration[3][curseura];
                    administration[2][curseura]++;
                    action--;
                    argent();
                    
                    try {
                        Parc(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    avecsauv[x1/50][y1/50] = "P";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment5 = 0;
                }
            }
            
            //si batiment5 = 3
        } else if (batiment5 == 3)
        {
            int curseura = batiment5-1;
            if (a<administration[0][curseura])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment5 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-administration[0][curseura];
                    b=b+administration[3][curseura];
                    administration[2][curseura]++;
                    action--;
                    argent();
                    
                    try {
                        Ecole(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    avecsauv[x1/50][y1/50] = "EC";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment5 = 0;
                }
            }
        } else if (batiment5 == 4)
        {
            int curseura = batiment5-1;
            if (a<administration[0][curseura])  //Check si le joueur a assez d'argent
            {
                g.setColor(Color.WHITE);
                g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 400, 30);
                font = new Font("Serif", Font.PLAIN, 20);
                g.setFont(font);
                g.setColor(java.awt.Color.decode("#000000"));
                g.drawString("Vous n'avez pas assez d'argent pour effectuer cela", longueur2/5, hauteur2/2);
                
                categories = 0;
                option = 0;
                batiment5 = 0;
                
            }else if ((x1 < longueur2) && (y1 < hauteur2))
            {
                //s'il y a de l'herbe
                if ("R".equals(avecsauv[x1/50][y1/50]))
                {
                    a=a-administration[0][curseura];
                    b=b+administration[3][curseura];
                    administration[2][curseura]++;
                    action--;
                    argent();
                    
                    try {
                        Gendarmerie(g, x1, y1);
                    } catch (IOException ex) {
                        Logger.getLogger(JeuISNavant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    avecsauv[x1/50][y1/50] = "G";
                    repaint();
                    
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect((longueur2/5)-5, (hauteur2/2)- 20, 257, 30);
                    font = new Font("Serif", Font.PLAIN, 20);
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawString("Un batiment y est dejà construit", longueur2 / 5, hauteur2 / 2);
                    
                    categories = 0;
                    option = 0;
                    batiment5 = 0;
                }
            }
            
        } else {
            reset();
        }
        g.setColor(java.awt.Color.decode("#ffffff"));
        g.fillRect(0, hauteur2, longueur, 130);
        
        font = new Font("Serif", Font.PLAIN, 11);
        g.setFont(font);
        g.setColor(java.awt.Color.decode("#000000"));
        g.drawString("Argent :", 55, hauteur2 + 10);
        g.drawString(String.valueOf(a) + " €", 50, hauteur2 + 30);
        g.drawString("Bonheur :", 155, hauteur2 + 10);
        g.drawString(String.valueOf((int)b), 155, hauteur2 + 30);
        g.drawString("Taux d'emploi :", 255, hauteur2 + 10);
        g.drawString(String.valueOf((int)e), 260, hauteur2 + 30);
        g.drawString("Pollution :", 355, hauteur2 + 10);
        g.drawString(String.valueOf((int)ptotale), 370, hauteur2 + 30);
        g.drawString("Nombre d'habitants :", 465, hauteur2 + 10);
        g.drawString(String.valueOf((int)nbrhabitants), 480, hauteur2 + 30);
        
        //jour/action
        if (action == 0)
        {
            day++;
            action = 3;
            passe = 0;
        }
        
        g.drawString("Jour :", longueur2 - 60, hauteur2 + 10);
        g.drawString(String.valueOf(day), longueur2 - 55, hauteur2 + 30);
        g.drawString("Nombre d'action :", longueur2 - 20, hauteur2 + 10);
        g.drawString(String.valueOf(action), longueur2, hauteur2 + 30);
        g.drawString("Niveau :", longueur2 - 120, hauteur2 + 10);
        g.drawString(String.valueOf(lvl), longueur2 - 110, hauteur2 + 30);
        
        if (nbrhabitants<=0)
        {
            JOptionPane.showMessageDialog(fenetre, "Vous avez perdu, dû à un manque d'habitants car vous avez " + (int)nbrhabitants + "habitants.");
            System.exit(0);
        } else if (a <= 0)
        {
            JOptionPane.showMessageDialog(fenetre, "Vous avez perdu, vous avez fait faillite car vous avez " + a + "€");
            System.exit(0);
        }
        this.addMouseListener(this);
    }
}