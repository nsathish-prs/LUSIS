using System;
using System.Collections.Generic;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class DeptDisbursementDetails : System.Web.UI.Page
    {
        readonly MainController mainController = new MainController();

        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            var disbursementID = Request.QueryString["disbursementID"];
            DisbursementID.Text = disbursementID;
            RepName.Text = Request.QueryString["RepName"];
            var RepID = Request.QueryString["RepID"];
            CollectPoint.Text = Request.QueryString["collectionPoint"];
            deptLabel.Text = Request.QueryString["deptName"];
            var status = Request.QueryString["Status"];

            //ButtonCancel.Visible = true;
            if (Session["Role"].Equals(("Clerk").ToString()) && status == "Ready")
            {
                ButtonCancel.Visible = true;
            }

            var disbursementItems = mainController.getDeptDisbItemList(Convert.ToInt32(disbursementID));

            if (disbursementItems.Count > 0)
            {
                GridView1.DataSource = disbursementItems;
                GridView1.DataBind();
            }

            if (status == "Retrieved")
            {
                Label1.Visible = true;
                SignatureLabel.Text = RepName.Text;
                SignatureLabel.Visible = true;
                ImageSignature.ImageUrl = "~/Signature/" + RepID + ".png";
                var url = (ImageSignature.ImageUrl).ToString();
                ImageSignature.Visible = true;
                //if (IsPostBack)
                //{
                //    bool b = isValidurl(url);

                //    if (b)
                //    {
                //        ImageSignature.Visible = true;

                //    }
                //}
            }
        }

        protected void ButtonCancel_Click(object sender, EventArgs e)
        {
            var selectedDisbID = Convert.ToInt32(DisbursementID.Text);
            mainController.CancelDisbursement(selectedDisbID);
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
                        authorization = true;
                        break;
                    case "Delegate":
                        authorization = true;
                        break;
                    case "Representative":
                        authorization = true;
                        break;
                    case "Employee":
                        authorization = false;
                        break;
                    case "Manager":
                        authorization = false;
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