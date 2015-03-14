using System;
using System.Collections.Generic;
using LUSIS_CONTROLLER.Controller;

namespace LUSIS_WEB_UI
{
    public partial class passwordReset : System.Web.UI.Page
    {
        MainController MainCon;
        string resetUname = "";
        Dictionary<string, string> newUnamepwd;
        protected void Page_Load(object sender, EventArgs e)
        {
            MainCon = new MainController();
            MultiView1.ActiveViewIndex = 0;
            resetUname = Request.QueryString["uName"].ToString();
        }

        protected void resetPwdBtn_Click(object sender, EventArgs e)
        {
            try
            {
                if (nPassword.Text == cPassword.Text)
                {
                    if (MainCon.updatePassword(resetUname, nPassword.Text))
                    {
                        newUnamepwd.Remove(resetUname);
                        Application["resetPwd"] = newUnamepwd;
                        Response.Write("<script>alert('Your Password has been successfully Changed.. Please Login again to continue.');</script>");
                        Response.Redirect("Login.aspx");
                    }
                    else
                    {
                        Response.Write("<script>alert('Something went Wrong... Please try again later..');</script>");
                    }

                }
            }
            catch (Exception ex)
            {
                Response.Write("<script>alert('" + ex.Message + "');</script>");
            }
        }

        protected void submitBtn_Click(object sender, EventArgs e)
        {
            newUnamepwd = (Dictionary<string, string>)Application["resetPwd"];
            if (newUnamepwd.ContainsKey(resetUname).ToString() == secCode.Text)
            {
                MultiView1.ActiveViewIndex = 1;
            }
            else
            {
                Response.Write("<script>alert('Please Enter the Correct Code sent to your Email');</script>");
            }
        }
    }
}