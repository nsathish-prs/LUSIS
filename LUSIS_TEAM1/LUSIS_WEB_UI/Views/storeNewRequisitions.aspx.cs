using System;
using LUSIS_CONTROLLER.Controller;

namespace LUSIS_WEB_UI.Views
{
    public partial class storeNewRequisitions : System.Web.UI.Page
    {
        readonly MainController controller = new MainController();
        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            //retByDept = facade.getRequistionByDepartment();
            gvStoreNewReq.DataSource = controller.selectRequistionByDepartment(); //--- added
            gvStoreNewReq.DataBind();
        }

        protected void btnGenRetrieval_Click(object sender, EventArgs e)
        {
            controller.generateRetrieval();
            Response.Redirect("storeRetrievalList.aspx");
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
                        authorization = false;
                        break;
                    case "Supervisor":
                        authorization = false;
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