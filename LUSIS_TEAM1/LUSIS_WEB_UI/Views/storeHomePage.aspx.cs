using System;
using System.Collections.Generic;
using LUSIS_EF_FACADE;
using LUSIS_CONTROLLER.Controller;


namespace LUSIS_WEB_UI.Views
{
    public partial class storeHomePage : System.Web.UI.Page
    {
        readonly MainController controller = new MainController();
        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            var newReqCount = controller.getNewRequisitionCount();
            lblNewReqCount.Text = newReqCount.ToString();

            var listPurRequired =  controller.getPurchaseRequired();
            if(listPurRequired != null)
            {
                lblPurReqCount.Text = listPurRequired.Count.ToString();
            }
            lblPendingDescCount.Text = controller.getPendingDiscrepancy().ToString();
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