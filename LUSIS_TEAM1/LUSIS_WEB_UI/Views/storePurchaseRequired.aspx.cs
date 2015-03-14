using System;
using System.Collections.Generic;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class storePurchaseRequired : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            itemsList();
            
        }

        public void itemsList()
        {
            var pc = new PurchaseController();
            var p = pc.itemList();

            if (p.Count != 0)
            {
                GridView1.DataSource = p;
                GridView1.DataBind();
                wow.Visible = false;
            }

            else
            {
                wow.Text = "You have all the items for the Minimum Quantity...!!";
                proceedOrder.Visible = false;
            }

        }

        protected void proceedOrder_Click(object sender, EventArgs e)
        {
            Response.Redirect("~/Views/storeProceedOrder.aspx");
        }

        private void checkAuthorization()
        {

            if (Session["Role"] == null)
                Response.Redirect("~/Login.aspx");

            var authorization = false;

            if (Session["Role"] != null)
            {
                switch (Convert.ToString(Session["Role"]))
                {
                    case "DeptHead":
                        authorization = false;
                        break;
                    case "Delegate":
                        authorization = false;
                        break;
                    case "Representative":
                        authorization = false;
                        break;
                    case "Employee":
                        authorization = false;
                        break;
                    case "Manager":
                        authorization = true;
                        break;
                    case "Supervisor":
                        authorization = true;
                        break;
                    case "Clerk":
                        authorization = true;
                        break;
                }
            }

            if (!authorization)
            {
                Response.Redirect("~/Views/Errorpage.aspx");
            }
        }
    }
}