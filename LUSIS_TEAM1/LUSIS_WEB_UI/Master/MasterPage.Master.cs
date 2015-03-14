using System;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Master
{
    public partial class MasterPage : System.Web.UI.MasterPage
    {
        Employee logon;
        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["Logon"] != null)
            {
                logon = (Employee)Session["Logon"];

                LblUname.Text = logon.Name + " (" + Session["Role"] + ")";
            }
            else
                Response.Redirect("~/Login.aspx");
        }

        protected void LnkHome_Click(object sender, EventArgs e)
        {
            if (Session["Logon"] != null)
            {
                logon = (Employee)Session["Logon"];

                if (Session["Role"] != null)
                {
                    var role = Session["Role"].ToString();
                    switch (role)
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
            else
                Response.Redirect("~/Login.aspx");
        }

        protected void LnkLogout_Click(object sender, EventArgs e)
        {
            Session.Abandon();
            //Response.Write("<script language='javascript'>alert('You have Logout !!');</script>");
            //Response.Write("<script language='javascript'>window.location.href = 'DeptRequisitionList.aspx';</script>");
            Response.Redirect("~/Login.aspx");
        }
    }
}