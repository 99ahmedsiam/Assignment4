/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manipulationapp;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 *
 * @author CH9319
 */
public class TableViewStudent implements Initializable {
    
     @FXML
    private TextField TextFieldId;
    @FXML
    private TextField TextFieldName;
    @FXML
    private TextField TextFieldMajor;
    @FXML
    private TextField TextFieldGrade;
    @FXML
    private TableView<Student> tableV;
    @FXML
    private TableColumn<Student, String> coulmnID;
    @FXML
    private TableColumn<Student, String> coulmnName;
    @FXML
    private TableColumn<Student, String> coulmnMajor;
    @FXML
    private TableColumn<Student, Double> coulmnGrade;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdite;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAddC;
    Statement statement;
    @FXML
    private Button showbtn;
    @FXML
    private TextArea Query;
    @FXML
    private Button performQuery;
    @FXML
    private Button updateDelBtn;
    @FXML
    private Button ShowCourse;
    @FXML
    private TextField IdStudent;
    @FXML
    private TextField IdCourse;
    @FXML
    private TextField smester;
    @FXML
    private TableView<Registration> tableCourse;
    @FXML
    private TableColumn<Registration, String> idStdC;
    @FXML
    private TableColumn<Registration, String> idCourseC;
    @FXML
    private TableColumn<Registration, String> smesterC;


  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
             Class.forName("com.mysql.jdbc.Driver");
            Connection connection =
               DriverManager.
                getConnection("jdbc:mysql://localhost:3306/students?zeroDateTimeBehavior=convertToNull","root","");
            this.statement = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        coulmnID.setCellValueFactory(new PropertyValueFactory("id"));
        coulmnName.setCellValueFactory(new PropertyValueFactory("name"));
        coulmnMajor.setCellValueFactory(new PropertyValueFactory("major"));
        coulmnGrade.setCellValueFactory(new PropertyValueFactory("grade"));
        idStdC.setCellValueFactory(new PropertyValueFactory("idStd"));
        idCourseC.setCellValueFactory(new PropertyValueFactory("idCourse"));
        smesterC.setCellValueFactory(new PropertyValueFactory("smester"));
        tableV.getSelectionModel().selectedItemProperty().addListener(
                event-> showSelectedStd());
        tableCourse.getSelectionModel().selectedItemProperty().addListener(
                event-> showSelectedCourse());
    }    
    
    @FXML
    private void buttonShowHandle(ActionEvent event) throws SQLException {
        ResultSet  rs=this.statement.executeQuery("Select * FROM student_info");
        tableV.getItems().clear();
        while(rs.next()){
            Student std=new Student();
            std.setId(rs.getString("id"));
            std.setName(rs.getString("name"));
            std.setMajor(rs.getString("major"));
            std.setGrade(rs.getDouble("grade"));
            tableV.getItems().add(std);
        }
    }
    
    @FXML
    private void buttonAddHandle(ActionEvent event) throws Exception {
        String id =TextFieldId.getText();
        String name=TextFieldName.getText();
        String major=TextFieldMajor.getText();
        double grade=Double.parseDouble(TextFieldGrade.getText());
        String sql="INSERT INTO student_info VALUES ( '"+id+"','"+name+"','"+major+"',"+grade+")";
        this.statement.executeUpdate(sql);
    }

    @FXML
    private void buttonEditeHandle(ActionEvent event) throws Exception {
   
        
        String id =TextFieldId.getText();
        String name=TextFieldName.getText();
        String major=TextFieldMajor.getText();
        double grade=Double.parseDouble(TextFieldGrade.getText());
        String sql="UPDATE student_info SET name=  '"+name+"',major='"+major+"',grade= "+grade +"WHERE id ='"+id+"'";
        this.statement.executeUpdate(sql);
    }

    @FXML
    private void buttonDeleteHandle(ActionEvent event) throws SQLException {
        String id1=TextFieldId.getText();
       
        
         String st = "DELETE FROM student_info WHERE id = " + id1 + ";";
        this.statement.executeUpdate(st);
    }

    @FXML
    private void buttonAddCHandle(ActionEvent event) throws SQLException {
        String idStudent1=IdStudent.getText();
        String idCourse1=IdCourse.getText();
        String smes=smester.getText();
        String sql="INSERT INTO `registration` VALUES ( '"+idStudent1+"','"+idCourse1+"','"+smes+"'"+")";
        this.statement.executeUpdate(sql);
    }
    
    @FXML
    private void ShowCourseHandle(ActionEvent event) throws SQLException {
        ResultSet  reg=this.statement.executeQuery("Select * FROM `registration` ");
        tableCourse.getItems().clear();
        while(reg.next()){
            Registration rg=new Registration();
            rg.setIdStd(reg.getString("studetnid"));
            rg.setIdCourse(reg.getString("courseid"));
            rg.setSmester(reg.getString("smester"));
            tableCourse.getItems().add(rg);
        }
    }
    
        @FXML
    private void PerformBtnHandle(ActionEvent event) throws SQLException {
        String content=Query.getText();
        content=content.replace("\"", "'");
        ResultSet rs=this.statement.executeQuery(content);
        tableV.getItems().clear();
        while(rs.next()){
            Student st=new Student();
            st.setId(rs.getString("id"));
            st.setName(rs.getString("name"));
            st.setMajor(rs.getString("major"));
            st.setGrade(rs.getDouble("grade"));
            tableV.getItems().add(st);
        }
    }
   
 
       private void showSelectedStd(){
        Student std = tableV.getSelectionModel().getSelectedItem();
        if(std != null){
        TextFieldId.setText((std.getId()));
        TextFieldName.setText(std.getName());
        TextFieldMajor.setText(std.getMajor());
        TextFieldGrade.setText(String.valueOf(std.getGrade()));
        }

    }
       private void showSelectedCourse(){
        Registration rg = tableCourse.getSelectionModel().getSelectedItem();
        if(rg != null){
        IdStudent.setText((rg.getIdStd()));
        IdCourse.setText(rg.getIdCourse());
        smester.setText(rg.getSmester());
        }

    }
       

    @FXML
    private void updateDelHandle(ActionEvent event) throws SQLException {
        String content=Query.getText();
        content=content.replace("\"", "'");
        this.statement.executeUpdate(content);
    }

    


 
}
    

