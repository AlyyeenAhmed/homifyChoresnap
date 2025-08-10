/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.housechoremanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.SourceVersion;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author dell
 */
public class Family extends javax.swing.JFrame {
     Connection conn;
    private Object jmembername;
    
    public Family() throws SQLException {
        this.conn = DBConnection.getConnection();
        initComponents();
        connectDatabase();
        populateTable();
    }
    
    private void connectDatabase() {
        try {
            Connection connection = DBConnection.getConnection();
            if (connection != null) {
                System.out.println("Connected to database");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void populateTable() {
        try {
            String sql = "SELECT MemberName FROM FamilyMembers";
            try (PreparedStatement statement = conn.prepareStatement(sql); ResultSet result = statement.executeQuery()) {
                
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);
                
                while (result.next()) {
                    String memberName = result.getString("MemberName");
                    model.addRow(new Object[]{memberName});
                }
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
     
    private void clearFields() {
        jmembername.setText("");
    }
     
    private void addMember() {
        String memberName = jmembername.getText().trim();
        
        if (memberName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a family member name.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            String sql = "INSERT INTO FamilyMembers (UserId, MemberName) VALUES (?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, 1); // Replace with the actual UserId of the logged-in user
                statement.setString(2, memberName);
                
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Family member added successfully.");
                    populateTable();
                    clearFields();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error adding family member: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
      
    private void updateMember() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a family member to update.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String newMemberName = jmembername.getText().trim();
        if (newMemberName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a new name for the family member.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            String currentMemberName = (String) jTable1.getValueAt(selectedRow, 0);
            String sql = "UPDATE FamilyMembers SET MemberName = ? WHERE MemberName = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, newMemberName);
            statement.setString(2, currentMemberName);
            
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Family member updated successfully.");
                populateTable();
                clearFields();
            }
            
            statement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating family member: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteMember() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a family member to delete.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String memberName = (String) jTable1.getValueAt(selectedRow, 0);
        
        try {
            String sql = "DELETE FROM FamilyMembers WHERE MemberName = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, memberName);
                
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Family member deleted successfully.");
                    populateTable();
                    clearFields();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting family member: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFMember = new javax.swing.JLabel();
        jmembername = new javax.swing.JTextField();
        jbtAdd = new javax.swing.JButton();
        jbtUpdate = new javax.swing.JButton();
        jbtDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jbtCancel = new javax.swing.JButton();
        jSave = new javax.swing.JButton();
        jBtback = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));

        jFMember.setText("Family Member");

        jbtAdd.setText("Add");
        jbtAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtAddActionPerformed(evt);
            }
        });

        jbtUpdate.setText("Update");
        jbtUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtUpdateActionPerformed(evt);
            }
        });

        jbtDelete.setText("Delete");
        jbtDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtDeleteActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Family Member"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jbtCancel.setText("Cancel");
        jbtCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCancelActionPerformed(evt);
            }
        });

        jSave.setText("Save");
        jSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSaveActionPerformed(evt);
            }
        });

        jBtback.setText("Back");
        jBtback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtbackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtAdd)
                        .addGap(64, 64, 64)
                        .addComponent(jbtUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtDelete)
                        .addGap(36, 36, 36))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jFMember, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(48, 48, 48)
                        .addComponent(jmembername, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtback)
                .addGap(44, 44, 44)
                .addComponent(jbtCancel)
                .addGap(41, 41, 41)
                .addComponent(jSave)
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFMember, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jmembername, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSave, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtback, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAddActionPerformed
        addMember();
    }//GEN-LAST:event_jbtAddActionPerformed

    private void jbtUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtUpdateActionPerformed
        updateMember();
    }//GEN-LAST:event_jbtUpdateActionPerformed

    private void jbtDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtDeleteActionPerformed
         deleteMember();
    }//GEN-LAST:event_jbtDeleteActionPerformed

    private void jBtbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtbackActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtbackActionPerformed

    private void jbtCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelActionPerformed
        clearFields();
    }//GEN-LAST:event_jbtCancelActionPerformed

    private void jSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSaveActionPerformed
          String memberName = jmembername.getText().trim(); // Get the entered member name from the text field
    
    if (memberName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a family member name.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return; // Exit method if the member name is empty
    }
    
    try {
              try (Connection con = DBConnection.getConnection() // Establish database connection
              ) {
                  PreparedStatement pstmt;
                  
                  // Check if a row is selected in the table for update, otherwise insert a new row
                  int selectedRow = jTable1.getSelectedRow();
                  if (selectedRow != -1) {
                      int memberId = (int) jTable1.getValueAt(selectedRow, 0); // Assuming MemberId is in the first column
                      String updateQuery = "UPDATE FamilyMembers SET MemberName = ? WHERE MemberId = ?";
                      pstmt = con.prepareStatement(updateQuery);
                      pstmt.setString(1, memberName);
                      pstmt.setInt(2, memberId);
                      pstmt.executeUpdate();
                      JOptionPane.showMessageDialog(this, "Family member updated successfully.", "Update", JOptionPane.INFORMATION_MESSAGE);
                  } else {
                      String insertQuery = "INSERT INTO FamilyMembers (UserId, MemberName) VALUES (?, ?)";
                      pstmt = con.prepareStatement(insertQuery);
                      pstmt.setString(2, memberName);
                      pstmt.executeUpdate();
                      JOptionPane.showMessageDialog(this, "New family member added successfully.", "Add", JOptionPane.INFORMATION_MESSAGE);
                  }
                  
                  pstmt.close();
              }
        
        clearFields(); // Clear input fields after save
        //loadFamilyMembers(); // Reload the table with updated data
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error saving family member: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jSaveActionPerformed
}
   
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Family.class.getName
            java.util.logging.Logger.getLogger(Family.class.getName
            java.util.logging.Logger.getLogger(Family.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_18;
    }class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Family.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Family.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Family.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
     java.awt.EventQueue.invokeLater(() -> {
            try {
                new Family().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(Family.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtback;
    private javax.swing.JLabel jFMember;
    private javax.swing.JButton jSave;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton jbtAdd;
    private javax.swing.JButton jbtCancel;
    private javax.swing.JButton jbtDelete;
    private javax.swing.JButton jbtUpdate;
    private javax.swing.JTextField jmembername;
    // End of variables declaration//GEN-END:variables

}