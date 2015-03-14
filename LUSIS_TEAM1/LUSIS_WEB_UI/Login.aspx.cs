using System;
using System.Collections.Generic;
using System.Web.Security;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI
{
    public partial class Login : System.Web.UI.Page
    {
        readonly Dictionary<string, string> unamePwd=new Dictionary<string,string>();
        MainController MainCon;
        string uName = "";
        string eMessageStart = "<b><a href='http://localhost:5000/passwordReset.aspx?uName=";
        string eMessageEnd = "'>Click Here</a></b> to reset your Password with the security code provided below. <br>";
        readonly string password = Membership.GeneratePassword(12, 1);
        protected void Page_Load(object sender, EventArgs e)
        {
            MainCon = new MainController();
            MultiView1.ActiveViewIndex = 0;
            if (Request.Params["X"] != null)
            {
                Session["Logon"] = null;
                Response.Redirect("~/Login.aspx");
            }
        }

        protected void BtnLogin_Click(object sender, EventArgs e)
        {
            

            var emp = MainCon.checkLogin(TxtUname.Text, TxtPasswd.Text);

            if (Session["Logon"] == null)
            {
                if (emp != null)
                {
                    Session["Logon"] = emp;
                    if (Session["Role"] == null)
                    {
                        if (emp.AdditionalRole == null)
                            Session["Role"] = emp.Role;
                        else
                            Session["Role"] = emp.AdditionalRole;

                        Session["Name"] = emp.Name;
                        Session["DeptCode"] = emp.DeptCode;
                        Session["EmpID"] = emp.EmpID;
                    }
                }
                else
                    Response.Write("<script language='javascript'>alert('Login Unsuccessful !!');</script>");
            }

            if (Session["Role"] != null)
            {
                switch (Convert.ToString(Session["Role"]))
                {
                    case "DeptHead":
                        Response.Redirect("~/Views/DeptHomePage.aspx");
                        break;
                    case "Delegate":
                        Response.Redirect("~/Views/DeptHomePage.aspx");
                        break;
                    case "Representative":
                        Response.Redirect("~/Views/DeptHomePage.aspx");
                        break;
                    case "Employee":
                        Response.Redirect("~/Views/DeptHomePage.aspx");
                        break;
                    case "Manager":
                        Response.Redirect("~/Views/storeHomePage.aspx");
                        break;
                    case "Supervisor":
                        Response.Redirect("~/Views/storeHomePage.aspx");
                        break;
                    case "Clerk":
                        Response.Redirect("~/Views/storeHomePage.aspx");
                        break;
                }
            }
        }

        protected void BtnPasswd_Click(object sender, EventArgs e)
        {
            MultiView1.ActiveViewIndex = 1;
        }

        protected void resetBtn_Click(object sender, EventArgs e)
        {
            uName = resetUname.Text;
            var eMail = regEmail.Text;
            try
            {
                var emp = MainCon.getEmployeeRecord(uName);
                if (eMail == emp.Email.ToString())
                {
                        unamePwd.Add(uName, password);
                        if (MainCon.sendEmail("A0120632@nus.edu.sg",eMail, "Password Reset", eMessageStart + uName + eMessageEnd + password))
                        {
                            Application["resetPwd"] = unamePwd;
                            Response.Write("<script>alert('Security Code has been sent to your registered email. Please check and Reset your password and proceed to Login');</script>");
                            MultiView1.ActiveViewIndex = 0;
                        }
                        else
                        {
                            Response.Redirect("~/Views/ErrorPage.aspx");
                        }
                }
            }
            catch (Exception ex)
            {
                Response.Write("<script>alert('" + ex.Message  + "');</script>");
            }
            
        }

        //protected void submitBtn_Click(object sender, EventArgs e)
        //{
        //    Employee emp = MainCon.getEmployeeRecord(resetUname.Text);
        //    Dictionary<string, string> newUnamepwd = (Dictionary<string, string>)Application["resetPwd"];
        //    if (emp.Password == secCode.Text)
        //    {
        //        MultiView1.ActiveViewIndex = 3;
        //    }
        //    else
        //    {
        //        Response.Write("<script>alert('Please Enter the Correct Code sent to your Email');</script>");
        //    }
        //}

        //protected void resetPwdBtn_Click(object sender, EventArgs e)
        //{
        //    try
        //    {
        //        if (nPassword.Text == cPassword.Text)
        //        {
        //            if (MainCon.updatePassword(resetUname.Text, nPassword.Text))
        //            {
        //                Response.Write("<script>alert('Your Password has been successfully Changed.. Please Login again to continue.');</script>");
        //            }
        //            else
        //            {
        //                Response.Write("<script>alert('Something went Wrong... Please try again later..');</script>");
        //            }
                    
        //        }
        //    }
        //    catch (Exception ex)
        //    {
        //        Response.Write("<script>alert('" + ex.Message +"');</script>");
        //    }
        //    MultiView1.ActiveViewIndex = 0;
        //}
    }
}