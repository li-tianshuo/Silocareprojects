package cc.shuozi.uidesign;

import androidx.appcompat.app.AppCompatActivity;

public class User extends AppCompatActivity {

    String email;
    String passwd;
    String fname;
    String lname;


    public User (String email , String passwd, String fname , String lname)
{
    this.email = email;
    this.passwd = passwd;
    this.fname = fname;
    this.lname= lname;

}



    public String getEmail() {
        return email;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }
}


