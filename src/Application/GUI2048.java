/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

/**
 *
 * @author Sylvain
 */
public class GUI2048 extends javax.swing.JFrame implements Parametres {

    /**
     * g : Grille pour lancer une nouvelle interface de l'application
     */
    private Grille g;

    /**
     * Creates new form GUI2048
     * Constructeur de l'interface qui permet d'initialiser les composants
     * La grille, les nouvelles cases et qui rafraichit la grille
     */
    public GUI2048() {
        initComponents();
        g = new Grille();
        boolean b = g.nouvelleCase();
        b = g.nouvelleCase();
        this.rafraichir();
    }

    /**
     * Permet de mettre à jour la grille : afficher les nouvelles cases de la grille
     */
    private void rafraichir() {
        labelGrille.setText(g.toHTML());
    }

    /**
     * Permet de bloquer l'utilisation des boutons directionnels en fin de partie
     * @param message Chaine de caractères qui affiche un message à la fin de la partie : message de victoire ou de défaite
     */
    private void finPartie(String message) {
        resultat.setText(message);
        boutonDroite.setEnabled(false);
        boutonGauche.setEnabled(false);
        boutonHaut.setEnabled(false);
        boutonBas.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelGrille = new javax.swing.JLabel();
        boutonHaut = new javax.swing.JButton();
        boutonGauche = new javax.swing.JButton();
        boutonDroite = new javax.swing.JButton();
        boutonBas = new javax.swing.JButton();
        resultat = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        boutonHaut.setText("^");
        boutonHaut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonHautMouseClicked(evt);
            }
        });

        boutonGauche.setText("<");
        boutonGauche.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonGaucheMouseClicked(evt);
            }
        });

        boutonDroite.setText(">");
        boutonDroite.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonDroiteMouseClicked(evt);
            }
        });

        boutonBas.setText("v");
        boutonBas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonBasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addComponent(boutonHaut))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(boutonBas)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(resultat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(boutonGauche)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(labelGrille, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(boutonDroite)))
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(resultat, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boutonHaut)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(boutonGauche)
                        .addGap(79, 79, 79))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(boutonDroite)
                                .addGap(76, 76, 76))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(labelGrille, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))))
                .addComponent(boutonBas)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Permet l'utilisation du bouton directionnel haut si le bouton n'est pas désactivé :
     *  - déplace les cases qui doivent l'être
     *  - génère une nouvelle case si possible, sinon annonce la défaite
     *  - met à jour la grille
     *  - vérifie si la partie est gagnée ou non, et affiche le score
     * @param evt Evénement lié au clic de la souris sur le bouton associé
     */
    private void boutonHautMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonHautMouseClicked
        // TODO add your handling code here:
        if (boutonHaut.isEnabled()) {
            boolean b2 = g.lanceurDeplacerCases(HAUT);
            if (b2) {
                boolean b = g.nouvelleCase();
                if (!b) {
                    this.finPartie("Perdu ! Score = "+g.getValeurMax());
                }
            }
            this.rafraichir();
            if (g.getValeurMax() >= OBJECTIF) {
                this.finPartie("Gagné ! Score = "+g.getValeurMax());
            }
            if (g.partieFinie()) this.finPartie("Perdu ! Score = "+g.getValeurMax());
        }
    }//GEN-LAST:event_boutonHautMouseClicked

    /**
     * Permet l'utilisation du bouton directionnel gauche si le bouton n'est pas désactivé :
     *  - déplace les cases qui doivent l'être
     *  - génère une nouvelle case si possible, sinon annonce la défaite
     *  - met à jour la grille
     *  - vérifie si la partie est gagnée ou non, et affiche le score
     * @param evt Evénement lié au clic de la souris sur le bouton associé
     */
    private void boutonGaucheMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonGaucheMouseClicked
        // TODO add your handling code here:
        if (boutonGauche.isEnabled()) {
            boolean b2 = g.lanceurDeplacerCases(GAUCHE);
            if (b2) {
                boolean b = g.nouvelleCase();
                if (!b) {
                    this.finPartie("Perdu ! Score = "+g.getValeurMax());
                }
            }
            this.rafraichir();
            if (g.getValeurMax() >= 2048) {
                this.finPartie("Gagné ! Score = "+g.getValeurMax());
            }
            if (g.partieFinie()) this.finPartie("Perdu ! Score = "+g.getValeurMax());
        }
    }//GEN-LAST:event_boutonGaucheMouseClicked

    /**
     * Permet l'utilisation du bouton directionnel drroite si le bouton n'est pas désactivé :
     *  - déplace les cases qui doivent l'être
     *  - génère une nouvelle case si possible, sinon annonce la défaite
     *  - met à jour la grille
     *  - vérifie si la partie est gagnée ou non, et affiche le score
     * @param evt Evénement lié au clic de la souris sur le bouton associé
     */
    private void boutonDroiteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonDroiteMouseClicked
        // TODO add your handling code here:
        if (boutonDroite.isEnabled()) {
            boolean b2 = g.lanceurDeplacerCases(DROITE);
            if (b2) {
                boolean b = g.nouvelleCase();
                if (!b) {
                    this.finPartie("Perdu ! Score = "+g.getValeurMax());
                }
            }
            this.rafraichir();
            if (g.getValeurMax() >= 2048) {
                this.finPartie("Gagné ! Score = "+g.getValeurMax());
            }
            if (g.partieFinie()) this.finPartie("Perdu ! Score = "+g.getValeurMax());
        }
    }//GEN-LAST:event_boutonDroiteMouseClicked
    
    /**
     * Permet l'utilisation du bouton directionnel bas si le bouton n'est pas désactivé :
     *  - déplace les cases qui doivent l'être
     *  - génère une nouvelle case si possible, sinon annonce la défaite
     *  - met à jour la grille
     *  - vérifie si la partie est gagnée ou non, et affiche le score
     * @param evt Evénement lié au clic de la souris sur le bouton associé
     */
    private void boutonBasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonBasMouseClicked
        // TODO add your handling code here:
        if (boutonBas.isEnabled()) {
            boolean b2 = g.lanceurDeplacerCases(BAS);
            if (b2) {
                boolean b = g.nouvelleCase();
                if (!b) {
                    this.finPartie("Perdu ! Score = "+g.getValeurMax());
                }
            }
            this.rafraichir();
            if (g.getValeurMax() >= 2048) {
                this.finPartie("Gagné ! Score = "+g.getValeurMax());
            }
            if (g.partieFinie()) this.finPartie("Perdu ! Score = "+g.getValeurMax());
        }
    }//GEN-LAST:event_boutonBasMouseClicked

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
            java.util.logging.Logger.getLogger(GUI2048.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI2048.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI2048.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI2048.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI2048().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boutonBas;
    private javax.swing.JButton boutonDroite;
    private javax.swing.JButton boutonGauche;
    private javax.swing.JButton boutonHaut;
    private javax.swing.JLabel labelGrille;
    private javax.swing.JLabel resultat;
    // End of variables declaration//GEN-END:variables
}
