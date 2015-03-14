using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class storeVoucher : System.Web.UI.Page
    {
        MainController MainCont;
        List<Discrepancy> disItemList = null;

        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            MainCont = new MainController();
            var vouID = "";

            if (Request.Params["VouID"] != null)
            {
                vouID = Request.Params["VouID"];
                disItemList = MainCont.getDiscrepancyVoucherItems(vouID);
            }

            if (disItemList != null)
            {
                String vouStatus = null;
                var pendList = MainCont.getPendDisVoucherItems(vouID);
                if (pendList == null)
                {
                    vouStatus = "Closed";
                }
                else
                {
                    if (pendList.Count() == 0)
                        vouStatus = "Closed";
                    else
                        vouStatus = "Pending";
                }

                LblHeadTitle.Text = "Discrepancy Voucher [" + vouStatus + "]";
                LblVoucherNo.Text = disItemList[0].VoucherID;
                LblDate.Text = disItemList[0].Date.Value.ToString("dd-MMM-yyyy");
                LblRaisedBy.Text = disItemList[0].Employee.Name;
                LblApprovalBy.Text = disItemList[0].EmployeeApp.Name;

                //Fill TableVouItems.
                //TableVouItems.FindControl("HeadAction").Visible = false;


                for (var i = 0; i < disItemList.Count; i++)
                {
                    var row = new TableRow();
                    var itemNo = new TableCell();
                    itemNo.Text = disItemList[i].ItemCode;
                    row.Cells.Add(itemNo);

                    var itemDescript = new TableCell();
                    itemDescript.Text = disItemList[i].Item.Description;
                    row.Cells.Add(itemDescript);

                    var qtyAdj = new TableCell();
                    qtyAdj.Text = disItemList[i].Quantity.ToString();
                    row.Cells.Add(qtyAdj);

                    var reason = new TableCell();
                    reason.Text = disItemList[i].Reason;
                    row.Cells.Add(reason);

                    //if (vouStatus == "Pending")
                    //{
                    //    TableVouItems.FindControl("HeadStatus").Visible = false;
                    //}
                    //else
                    //{
                        var status = new TableCell();
                        status.Text = disItemList[i].Status;
                        row.Cells.Add(status);
                    //}

                    TableVouItems.Rows.Add(row);
                }
            }
        }

        protected void BtnBack_Click(object sender, EventArgs e)
        {
            Response.Redirect("~/Views/storeVoucherList.aspx"); 
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