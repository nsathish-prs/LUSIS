using System;

namespace LUSIS_WEB_UI.Views
{
    public partial class storeGenerateOrders : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
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


        protected void subOrder_Click(object sender, EventArgs e)
        {
            var fc = new LUSIS_EF_FACADE.Facade.EF_Facade();
            fc.requisitionEmail();
        }
    }

}