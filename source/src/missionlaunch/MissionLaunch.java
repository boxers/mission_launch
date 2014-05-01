package missionlaunch;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class MissionLaunch extends javax.swing.JFrame {
    VisualViewPort visualViewPort = new VisualViewPort();
    Visuals missionVisual = new MissionVisual(visualViewPort);
    //Visuals missionVisual = new OrbitSimulation(visualViewPort);
    GregorianCalendar date = new GregorianCalendar();
    
    ABody sun = ABody.SUN.copy();
    ABody mercury = ABody.MERCURY.copy();
    ABody venus = ABody.VENUS.copy();
    ABody earth = ABody.EARTH.copy();
    ABody mars = ABody.MARS.copy();
    ABody jupiter = ABody.JUPITER.copy();
    ABody saturn = ABody.SATURN.copy();
    ABody uranus = ABody.URANUS.copy();
    ABody neptune = ABody.NEPTUNE.copy();
    
    Shuttle shuttle;
    
    Thread animator;
    
    public MissionLaunch() {
        initComponents();
        postInit();
    }
    
    private void animateLaunch(final Shuttle shuttle){
        resultsTextArea.setText("");
        launchButton.setEnabled(false);
        launchDayCBox.setEnabled(false);
        launchMonthCBox.setEnabled(false);
        launchYearCBox.setEnabled(false);
        originCBox.setEnabled(false);
        destinationCBox.setEnabled(false);
        animator = new Thread(){
            public void run(){
                shuttle.calculateTravelPath();
                if(shuttle.isCollision()){
                    resultsTextArea.append("Mission failed.\n");
                    resultsTextArea.append("Shuttle collided with "+shuttle.getCollision());
                }
                else{
                    if(!shuttle.isStopped()){
                        resultsTextArea.append("Mission success!\n");
                        //SimpleDateFormat sdf = new SimpleDateFormat("MMMMM d, yyyy. h a");
                        //resultsTextArea.append("Shuttle arrived on "+sdf.format(shuttle.getDate().getTime()));
                    }
                }
                launchButton.setEnabled(true);
                launchDayCBox.setEnabled(true);
                launchMonthCBox.setEnabled(true);
                launchYearCBox.setEnabled(true);
                originCBox.setEnabled(true);
                destinationCBox.setEnabled(true);
            }
        };
        animator.start();
    }
    
    private ABody getABody(String body){
        if(body.equalsIgnoreCase("mercury")){
            return mercury;
        }
        else if(body.equalsIgnoreCase("venus")){
            return venus;
        }
        else if(body.equalsIgnoreCase("earth")){
            return earth;
        }
        else if(body.equalsIgnoreCase("mars")){
            return mars;
        }
        else if(body.equalsIgnoreCase("jupiter")){
            return jupiter;
        }
        else if(body.equalsIgnoreCase("saturn")){
            return saturn;
        }
        else if(body.equalsIgnoreCase("uranus")){
            return uranus;
        }
        else if(body.equalsIgnoreCase("neptune")){
            return neptune;
        }
        else{
            return sun;
        }  
    }
    
    private void launchShuttle(){
        missionVisual.resetDrawingBoard();
        String origin = (String)originCBox.getSelectedItem();
        String destination = (String)destinationCBox.getSelectedItem();
        ABody src = getABody(origin);
        ABody dest = getABody(destination);
        int m = launchMonthCBox.getSelectedIndex();
        String y = (String)launchYearCBox.getSelectedItem();
        String d = (String)launchDayCBox.getSelectedItem();
        GregorianCalendar launchDate = new GregorianCalendar(Integer.parseInt(y),m,Integer.parseInt(d));
        int velocity = Integer.parseInt(velocityField.getText());
        shuttle = new Shuttle(src,dest,launchDate,missionVisual, velocity);
        animateLaunch(shuttle);
    }
    
    private void postInit(){
        int month = date.get(GregorianCalendar.MONTH);
        int year = date.get(GregorianCalendar.YEAR);
        int day = date.get(GregorianCalendar.DAY_OF_MONTH);
        launchMonthCBox.setSelectedIndex(month);
        launchYearCBox.setSelectedItem(""+year);
        launchDayCBox.setSelectedItem(""+day);
    }
    
    public void updateLaunchDays(int m, int y){
        GregorianCalendar temp = new GregorianCalendar(y,m,1);
        int daysInMonth = temp.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        String[] days = new String[daysInMonth];
        for(int i = 0; i < days.length; i++){
            days[i] = ""+(i+1);
        }
        String day = (String)launchDayCBox.getSelectedItem();
        launchDayCBox.setModel(new javax.swing.DefaultComboBoxModel(days));
        if(Integer.parseInt(day) > days.length){
            day = ""+1;
        }
        launchDayCBox.setSelectedItem(day);
        updatePlanetPositions(new GregorianCalendar(y,m,Integer.parseInt(day)));
    }
    
    public void updatePlanetPositions(GregorianCalendar d){
        missionVisual.resetDrawingBoard();
        missionVisual.calculatePlanetPositions(d);
    }
    
    public void updateOrbitScope(){
        String origin = (String)originCBox.getSelectedItem();
        String destination = (String)destinationCBox.getSelectedItem();
        if(origin.equalsIgnoreCase("neptune") || destination.equalsIgnoreCase("neptune")){
            missionVisual.changeScope(MissionVisual.NEPTUNE_APHELION);
        }
        else if(origin.equalsIgnoreCase("uranus") || destination.equalsIgnoreCase("uranus")){
            missionVisual.changeScope(MissionVisual.URANUS_APHELION);
        }
        else if(origin.equalsIgnoreCase("saturn") || destination.equalsIgnoreCase("saturn")){
            missionVisual.changeScope(MissionVisual.SATURN_APHELION);
        }
        else if(origin.equalsIgnoreCase("jupiter") || destination.equalsIgnoreCase("jupiter")){
            missionVisual.changeScope(MissionVisual.JUPITER_APHELION);
        }
        else{
            missionVisual.changeScope(MissionVisual.MARS_APHELION);
        }
        int m = launchMonthCBox.getSelectedIndex();
        String y = (String)launchYearCBox.getSelectedItem();
        String d = (String)launchDayCBox.getSelectedItem();
        GregorianCalendar launchDate = new GregorianCalendar(Integer.parseInt(y),m,Integer.parseInt(d));
        updatePlanetPositions(launchDate);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        visualScroller = new javax.swing.JScrollPane();
        visualPanel = (javax.swing.JPanel)missionVisual;
        launchDatePanel = new javax.swing.JPanel();
        launchDayLabel = new javax.swing.JLabel();
        launchDayCBox = new javax.swing.JComboBox();
        launchMonthLabel = new javax.swing.JLabel();
        launchMonthCBox = new javax.swing.JComboBox();
        launchYearCBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        flightPathPanel = new javax.swing.JPanel();
        originLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        originCBox = new javax.swing.JComboBox();
        destinationCBox = new javax.swing.JComboBox();
        launchButton = new javax.swing.JButton();
        resultsScroller = new javax.swing.JScrollPane();
        resultsTextArea = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        velocityField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1280, 740));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        visualScroller.setViewport(visualViewPort);
        visualScroller.setMinimumSize(new java.awt.Dimension(900, 710));
        visualScroller.setPreferredSize(new java.awt.Dimension(900, 710));

        javax.swing.GroupLayout visualPanelLayout = new javax.swing.GroupLayout(visualPanel);
        visualPanel.setLayout(visualPanelLayout);
        visualPanelLayout.setHorizontalGroup(
            visualPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1188, Short.MAX_VALUE)
        );
        visualPanelLayout.setVerticalGroup(
            visualPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 788, Short.MAX_VALUE)
        );

        visualScroller.setViewportView(visualPanel);

        getContentPane().add(visualScroller, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        launchDatePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Launch Date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        launchDatePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        launchDayLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        launchDayLabel.setText(" Day");
        launchDatePanel.add(launchDayLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 30, 20));

        launchDayCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1" }));
        launchDayCBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                launchDayCBoxItemStateChanged(evt);
            }
        });
        launchDatePanel.add(launchDayCBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, -1, -1));

        launchMonthLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        launchMonthLabel.setText("Month");
        launchDatePanel.add(launchMonthLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 20));

        launchMonthCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January",
            "February","March","April","May","June","July","August","September","October",
            "November","December"}));
launchMonthCBox.addItemListener(new java.awt.event.ItemListener() {
    public void itemStateChanged(java.awt.event.ItemEvent evt) {
        launchMonthCBoxItemStateChanged(evt);
    }
    });
    launchDatePanel.add(launchMonthCBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

    launchYearCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2013","2014","2015","2016","2017","2018","2019","2020" }));
    launchYearCBox.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(java.awt.event.ItemEvent evt) {
            launchYearCBoxItemStateChanged(evt);
        }
    });
    launchDatePanel.add(launchYearCBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, -1));

    jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel1.setText("Year");
    launchDatePanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, -1, 20));

    getContentPane().add(launchDatePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 130, 370, 90));

    flightPathPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Flight Path", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
    flightPathPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    originLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    originLabel.setText("Origin");
    flightPathPanel.add(originLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 20));

    jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel2.setText("Destination");
    flightPathPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, -1, 20));

    originCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mercury",
        "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune" }));
originCBox.addItemListener(new java.awt.event.ItemListener() {
public void itemStateChanged(java.awt.event.ItemEvent evt) {
    originCBoxItemStateChanged(evt);
    }
    });
    flightPathPanel.add(originCBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 100, -1));

    destinationCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mercury",
        "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Sun" }));
destinationCBox.addItemListener(new java.awt.event.ItemListener() {
public void itemStateChanged(java.awt.event.ItemEvent evt) {
    destinationCBoxItemStateChanged(evt);
    }
    });
    flightPathPanel.add(destinationCBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 100, -1));

    getContentPane().add(flightPathPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 20, 370, 90));

    launchButton.setText("Launch");
    launchButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            launchButtonActionPerformed(evt);
        }
    });
    getContentPane().add(launchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 320, -1, -1));

    resultsTextArea.setEditable(false);
    resultsTextArea.setColumns(20);
    resultsTextArea.setLineWrap(true);
    resultsTextArea.setRows(5);
    resultsTextArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Results", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
    resultsScroller.setViewportView(resultsTextArea);

    getContentPane().add(resultsScroller, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 360, 360, 350));

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Velocity", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
    jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel3.setText("Starting Velocity");
    jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 90, 30));
    jPanel1.add(velocityField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 120, 30));

    getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 230, 370, 80));

    jButton1.setText("Stop");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });
    getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 320, 70, -1));

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void launchMonthCBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_launchMonthCBoxItemStateChanged
        int m = launchMonthCBox.getSelectedIndex();
        String y = (String)launchYearCBox.getSelectedItem();
        updateLaunchDays(m,Integer.parseInt(y));
    }//GEN-LAST:event_launchMonthCBoxItemStateChanged

    private void launchDayCBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_launchDayCBoxItemStateChanged
        int m = launchMonthCBox.getSelectedIndex();
        String y = (String)launchYearCBox.getSelectedItem();
        String d = (String)launchDayCBox.getSelectedItem();
        updatePlanetPositions(new GregorianCalendar(Integer.parseInt(y),m,Integer.parseInt(d)));
    }//GEN-LAST:event_launchDayCBoxItemStateChanged

    private void launchYearCBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_launchYearCBoxItemStateChanged
        int m = launchMonthCBox.getSelectedIndex();
        String y = (String)launchYearCBox.getSelectedItem();
        updateLaunchDays(m,Integer.parseInt(y));
    }//GEN-LAST:event_launchYearCBoxItemStateChanged

    private void originCBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_originCBoxItemStateChanged
        updateOrbitScope();
    }//GEN-LAST:event_originCBoxItemStateChanged

    private void destinationCBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_destinationCBoxItemStateChanged
        updateOrbitScope();
    }//GEN-LAST:event_destinationCBoxItemStateChanged

    private void launchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_launchButtonActionPerformed
        launchShuttle();
    }//GEN-LAST:event_launchButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(shuttle != null){
            shuttle.stopShuttle();
            shuttle = null;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(MissionLaunch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MissionLaunch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MissionLaunch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MissionLaunch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MissionLaunch().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox destinationCBox;
    private javax.swing.JPanel flightPathPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton launchButton;
    private javax.swing.JPanel launchDatePanel;
    private javax.swing.JComboBox launchDayCBox;
    private javax.swing.JLabel launchDayLabel;
    private javax.swing.JComboBox launchMonthCBox;
    private javax.swing.JLabel launchMonthLabel;
    private javax.swing.JComboBox launchYearCBox;
    private javax.swing.JComboBox originCBox;
    private javax.swing.JLabel originLabel;
    private javax.swing.JScrollPane resultsScroller;
    private javax.swing.JTextArea resultsTextArea;
    private javax.swing.JTextField velocityField;
    private javax.swing.JPanel visualPanel;
    private javax.swing.JScrollPane visualScroller;
    // End of variables declaration//GEN-END:variables
}
