using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class DeptViewRequisition : System.Web.UI.Page
    {
        readonly MainController MainCon = new MainController();
        private Employee logonEmp = new Employee();
        private Requisition req = null;

        protected void Page_Load(object sender, EventArgs e)
        {
            var MainCon = new MainController();
            var reqID = "";

            BtnNewReq.Visible = false;
            logonEmp = (Employee) Session["Logon"];

            if (Request.Params["ReqID"] != null)
            {
                reqID = Request.Params["ReqID"];
            }
            
            req = MainCon.GetRequisitionById(reqID);


            if (req != null)
            {
                LblHeadTitle.Text = "Requisition [" + req.ApprovalStatus + "]";
                RequisitionID.Text = req.RequisitionID;
                EmpName.Text = req.Employee.Name;
                TxtComments.ReadOnly = true;
                TxtComments.Text = req.RequisitionComment;
                if (req.ApprovalStatus == "Rejected")
                {
                    TxtRComments.ReadOnly = true;
                    TxtRComments.Text = req.RejectionComment;
                    if (req.EmpID == logonEmp.EmpID) 
                        BtnNewReq.Visible = true;
                }
                else
                {
                    LblRComments.Visible = false;
                    TxtRComments.Visible = false;
                }
            }

            var reqDetails = MainCon.GetReqDetailsByReqId(reqID);

            //Fill TableReqDetail.
            TableReqDetail.FindControl("HeadAction").Visible = false;

            if (reqDetails != null)
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

                    TableReqDetail.Rows.Add(row);
                }
            }
        }

        protected void BtnBack_Click(object sender, EventArgs e)
        {
            Response.Redirect("~/Views/DeptRequisitionList.aspx"); 
        }

        protected void BtnNewReq_Click(object sender, EventArgs e)
        {
            Button control = (Button)sender;
            
            //req = MainCon.getRequisitionByID(control.Text);
            if (req != null)
            {
                if (req.EmpID == logonEmp.EmpID && req.ApprovalStatus == "Rejected")
                    Response.Redirect("~/Views/DeptNewRequisition.aspx?ReqID=" + req.RequisitionID);
                else
                    Response.Redirect("~/Views/DeptViewRequisition.aspx?ReqID=" + req.RequisitionID);
            }
            Response.Redirect("~/Views/DeptRequisitionList.aspx");
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
                        authorization = true;
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