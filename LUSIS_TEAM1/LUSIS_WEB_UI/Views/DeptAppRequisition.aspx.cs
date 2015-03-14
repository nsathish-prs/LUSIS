using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class DeptAppRequisition : System.Web.UI.Page
    {
        MainController MainCon;
        Requisition requisition;
        Employee em;

        protected void Page_Load(object sender, EventArgs e)
        {
            MainCon = new MainController();
            var reqID = "";
            em = (Employee)Session["Logon"];
            checkAuthorization();

            if (Request.Params["ReqID"] != null)
            {
                reqID = Request.Params["ReqID"];
            }
            
            requisition = MainCon.GetRequisitionById(reqID);

            if (requisition != null)
            {
                LblRequisitionID.Text = requisition.RequisitionID;
                LblEmpName.Text = requisition.Employee.Name;
                TxtComments.ReadOnly = true;
            }

            var reqDetails = MainCon.GetReqDetailsByReqId(reqID);

            //Fill TableAppReq.
            //TableAppReq.FindControl("HeadAction").Visible = false;

            if (reqDetails.Count > 0)
            {

                for (var i = 0; i < reqDetails.Count; i++)
                {
                    var row = new TableRow();
                    var itemCategory = new TableCell();
                    itemCategory.Text = reqDetails[i].Item.Category;
                    row.Cells.Add(itemCategory);

                    var itemDescript = new TableCell();
                    itemDescript.Text = reqDetails[i].Item.Description;
                    row.Cells.Add(itemDescript);

                    var unitMeasure = new TableCell();
                    unitMeasure.Text = reqDetails[i].Item.UnitOfMeasure;
                    row.Cells.Add(unitMeasure);

                    var quantiy = new TableCell();
                    quantiy.Text = reqDetails[i].Quantity.ToString();
                    row.Cells.Add(quantiy);

                    TableAppReq.Rows.Add(row);
                }
            }
            else
                TableAppReq.Visible = false;
        }

        protected void BtnBack_Click(object sender, EventArgs e)
        {
            Response.Redirect("~/Views/DeptAppRequisitionList.aspx"); 
        }

        protected void BtnApprove_Click(object sender, EventArgs e)
        {
            //requisition.RejectionComment = TxtRComments.Text;
            //requisition.ApprovalStatus = "Approved";
            //MainCon.setRequisition(requisition);
            //MainCon.sendEmail(em.Email, requisition.Employee.Email, "Requisition Status", "Your Requisition status has been changed.");
            MainCon.approveNotification(requisition);
            Response.Redirect("~/Views/DeptAppRequisitionList.aspx"); 
        }

        protected void BtnReject_Click(object sender, EventArgs e)
        {
            requisition.RejectionComment = TxtRComments.Text;
            requisition.ApprovalStatus = "Rejected";
            requisition.Status = "Closed";
            MainCon.setRequisition(requisition);
            MainCon.sendEmail(em.Email, requisition.Employee.Email, "Requisition Status", "Your Requisition status has been changed.");
            Response.Redirect("~/Views/DeptAppRequisitionList.aspx"); 
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
                        authorization = false;
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