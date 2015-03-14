using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class storeAppVoucher : System.Web.UI.Page
    {
        MainController MainCont;
        String vouID = "";
        List<Discrepancy> pendList = null;
        Employee emp;
        protected void Page_Load(object sender, EventArgs e)
        {
            MainCont = new MainController();
            checkAuthorization();
            emp = (Employee)Session["Logon"];
            if (Request.Params["VouID"] != null)
            {
                vouID = Request.Params["VouID"];
                pendList = MainCont.getPendDisVoucherItems(vouID);
            }

            if (pendList != null)
            {
                //String vouStatus = null;
                //if (disItemList[0].Status == "Pending")
                //    vouStatus = "Pending";
                //else
                //    vouStatus = "Closed";
                //LblHeadTitle.Text = "Discrepancy Voucher [" + vouStatus + "]";
                LblVoucherNo.Text = pendList[0].VoucherID;
                LblDate.Text = pendList[0].Date.Value.ToString("dd-MMM-yyyy");
                LblRaisedBy.Text = pendList[0].Employee.Name;
                //LblApprovalBy.Text = disItemList[0].EmployeeApp.Name;

                //Fill TableVouItems.
                //TableVouItems.FindControl("HeadAction").Visible = false;


                for (var i = 0; i < pendList.Count; i++)
                {
                    var row = new TableRow();
                    var itemNo = new TableCell();
                    itemNo.Text = pendList[i].ItemCode;
                    row.Cells.Add(itemNo);

                    var itemDescript = new TableCell();
                    itemDescript.Text = pendList[i].Item.Description;
                    row.Cells.Add(itemDescript);

                    var qtyAdj = new TableCell();
                    qtyAdj.Text = pendList[i].Quantity.ToString();
                    row.Cells.Add(qtyAdj);

                    var reason = new TableCell();
                    reason.Text = pendList[i].Reason;
                    row.Cells.Add(reason);

                    var approval = new TableCell();
                    if (pendList[i].Status == "Pending")
                    {
                        var checkbox = new CheckBox();
                        checkbox.ID = pendList[i].DiscrepancyID.ToString();
                        approval.Controls.Add(checkbox);
                    }
                    row.Cells.Add(approval);

                    //if (vouStatus == "Pending")
                    //{
                    //    TableVouItems.FindControl("HeadStatus").Visible = false;
                    //}
                    //else
                    //{
                    //    TableCell status = new TableCell();
                    //    status.Text = disItemList[i].Status;
                    //    row.Cells.Add(status);
                    //}

                    TableVouItems.Rows.Add(row);
                }
            }
        }

        protected List<Discrepancy> getCheckList()
        {
            var checkList = new List<Discrepancy>();
            for (var i = 1; i < TableVouItems.Rows.Count; i++)
            {

                //HttpCookie Test = new HttpCookie("Test", "");
                //Test.Value = pendList.Count.ToString();
                //Response.AppendCookie(Test);

                CheckBox c = null;
                if (i < pendList.Count + 1)
                    c = (CheckBox)TableVouItems.Rows[i].Cells[4].FindControl(pendList[i - 1].DiscrepancyID.ToString());

                if (c != null)
                {
                    if (c.Checked)
                    {
                        var d = new Discrepancy();
                        d.DiscrepancyID = (Convert.ToInt32(c.ID));
                        checkList.Add(d);
                    }
                }
            }

            if (checkList.Count > 0)
                return checkList;
            else
                return null;
        }

        protected void BtnBack_Click(object sender, EventArgs e)
        {
            Response.Redirect("~/Views/storeAppVoucherList.aspx");
        }

        protected void BtnApprove_Click(object sender, EventArgs e)
        {
            // Update Approval Status = Approved 
            var checkList = getCheckList();

            if (checkList != null)
            {
                foreach (var d in checkList)
                {
                    d.Status = "Approved";
                    MainCont.sendEmail(emp.Email, d.Employee.Email, "Voucher Status Change", "The Discrepency Voucher Raised " + d.VoucherID + "has been updated. Check and update the Inventory");
                }
                MainCont.setDiscrepancyList(checkList);
                foreach (var d in checkList)
                {
                    foreach (var p in pendList)
                    {
                        if (d.DiscrepancyID == p.DiscrepancyID)
                        {
                            MainCont.UpdateInventory(p.ItemCode, p.Quantity.ToString());
                            MainCont.AddNewTransaction(p.ItemCode, p.VoucherID, p.DiscrepancyID, p.Quantity, "Discrepancy");
                        }
                    }
                }
            }

            // Redirect to own page if more pending else to appVouList page.
            if (MainCont.getPendDisVoucherItems(vouID).Count > 0)
            {
                Response.Redirect("~/Views/storeAppVoucher.aspx?VouID=" + vouID);
            }
            else
                Response.Redirect("~/Views/storeAppVoucherList.aspx");
        }

        protected void BtnReject_Click(object sender, EventArgs e)
        {
            //Update Approval Status = Rejected
            var checkList = getCheckList();

            if (checkList != null)
            {
                foreach (var d in checkList)
                {
                    d.Status = "Rejected";
                    MainCont.sendEmail("", d.Employee.Email, "Voucher Status Change", "The Discrepency Voucher Raised " + d.VoucherID + "has been updated. Check and update the Inventory");
                }
                MainCont.setDiscrepancyList(checkList);
            }

            // Redirect to own page if more pending else to appVouList page.
            if (MainCont.getPendDisVoucherItems(vouID).Count > 0)
            {
                Response.Redirect("~/Views/storeAppVoucher.aspx?VouID=" + vouID);
            }
            else
                Response.Redirect("~/Views/storeAppVoucherList.aspx");
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