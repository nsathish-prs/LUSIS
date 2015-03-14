using System;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class DeptNewRequisition : System.Web.UI.Page
    {
        readonly MainController Controller = new MainController();
        Employee emp = new Employee();
        Requisition req = null;
        String status = "";

        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            displayControls(false);
            status = LblErrorText.Text;
            LblErrorText.Text = "";
            LblReqID.Visible = false;
            
            if (Session["Logon"] != null)
            {
                emp = (Employee)Session["Logon"];
                EmpName.Text = emp.Name;

                // Check for New Requisition !!
                if (Request.Params["N"] != null)
                {
                    Session["selectedItems"] = null;
                }

                // Load DeptNewRequisition.aspx in EditNewRequisition mode.
                if (Request.Params["ReqID"] != null)
                {
                    displayControls(true);
                    req = Controller.GetRequisitionById(Request.Params["ReqID"].ToString());
                    if (req != null)
                    {
                        if (req.ApprovalStatus == "Pending" || req.ApprovalStatus == "Rejected" && req.EmpID == emp.EmpID)
                        {
                            LblHeadTitle.Text = "Requisition [Edit]";
                            LblReqID.Visible = true;
                            LblReqID.Text = "Requisition ID : " + Request.Params["ReqID"];
                            BtnSubmit.Text = "Save";

                            if (req.ApprovalStatus == "Rejected")
                            {
                                LblHeadTitle.Text = "Requisition [New]";
                                LblReqID.Visible = false;
                                //LblReqID.Text = "Requisition ID : " + Request.Params["ReqID"];
                                BtnSubmit.Text = "Submit";
                            }

                            if (IsPostBack == false)
                                textReqComment.Text = req.RequisitionComment;

                            var reqDetails = Controller.GetReqDetailsByReqId(Request.Params["ReqID"].ToString());

                            var listSelectedItems = new List<Item>();

                            if (IsPostBack == false && Session["selectedItems"] == null)
                            {
                                for (var i = 0; i < reqDetails.Count; i++)
                                {
                                    var temp = new Item();
                                    temp.ItemCode = reqDetails[i].ItemCode;
                                    temp.Category = reqDetails[i].Item.Category;
                                    temp.Description = reqDetails[i].Item.Description;
                                    temp.UnitOfMeasure = reqDetails[i].Item.UnitOfMeasure;
                                    temp.CurrentQuantity = reqDetails[i].Quantity;
                                    listSelectedItems.Add(temp);
                                }

                                Session["selectedItems"] = listSelectedItems;
                            }

                            listSelectedItems = (List<Item>)Session["selectedItems"];
                            var tcount = 0;
                            var textChange = new EventHandler(txtQty_textChange);

                            for (var i = 0; i < listSelectedItems.Count; i++)
                            {
                                var item = listSelectedItems[i];
                                var icode = Controller.selectItemByDescription(listSelectedItems[i].Description);

                                var row = new TableRow();
                                var category = new TableCell();
                                category.Text = icode.Category;
                                row.Cells.Add(category);

                                var description = new TableCell();
                                description.Text = item.Description;
                                row.Cells.Add(description);

                                var unit = new TableCell();
                                unit.Text = icode.UnitOfMeasure;
                                row.Cells.Add(unit);

                                //validator to check number.
                                
                                var txtQty = new TextBox();
                                txtQty.ID = "idQty" + tcount;
                                txtQty.Text = item.CurrentQuantity.ToString();
                                txtQty.AutoPostBack = true;
                                txtQty.PreRender += textChange;
                                
                                var qty = new TableCell();
                                qty.Controls.Add(txtQty);
                                row.Cells.Add(qty);

                                var btnDelete = new Button();
                                btnDelete.ID = "idDelete" + tcount;
                                btnDelete.Text = "Delete";
                                btnDelete.Click += new EventHandler(btnDelete_Click);
                                var action = new TableCell();
                                action.Controls.Add(btnDelete);
                                row.Cells.Add(action);
                                tcount++;

                                TableNewReq.Rows.Add(row);
                            }
                        }
                    }
                }
                else if (Session["selectedItems"] != null)
                {
                    displayControls(true);
                    var listSelectedItems = (List<Item>)Session["selectedItems"];

                    var tmplistSelectedItem = new List<Item>();
                    var count = 0;
                    var textChange = new EventHandler(txtQty_textChange);

                    for (var i = 0; i < listSelectedItems.Count; i++)
                    {
                        var item = Controller.selectItemByDescription(listSelectedItems[i].Description);

                        var row = new TableRow();
                        var category = new TableCell();
                        category.Text = item.Category;
                        row.Cells.Add(category);

                        var description = new TableCell();
                        description.Text = item.Description;
                        row.Cells.Add(description);

                        var unit = new TableCell();
                        unit.Text = item.UnitOfMeasure;
                        row.Cells.Add(unit);

                        //validator to check number.
                        var txtQty = new TextBox();
                        txtQty.ID = "idQty" + count;
                        txtQty.Text = listSelectedItems[i].CurrentQuantity.ToString(); //Requisition quantity is saved tempararily in current quantity. 
                        txtQty.AutoPostBack = true;
                        txtQty.PreRender += textChange;
                        var qty = new TableCell();
                        qty.Controls.Add(txtQty);
                        row.Cells.Add(qty);

                        //CustomValidator CVQty = new CustomValidator();
                        //CVQty.ID = "CVQty" + count;
                        //CVQty.ControlToValidate = txtQty.ID;
                        //CVQty.ErrorMessage = "Error :: Quantity not valid (Require > 0, Max: 1 to 8 Digits).";
                        //CVQty.ServerValidate += Check_QInteger;

                        var btnDelete = new Button();
                        btnDelete.ID = "idDelete" + count;
                        btnDelete.Text = "Delete";
                        btnDelete.Click += new EventHandler(btnDelete_Click);
                        var action = new TableCell();
                        action.Controls.Add(btnDelete);
                        row.Cells.Add(action);
                        count++;

                        TableNewReq.Rows.Add(row);

                        tmplistSelectedItem.Add(item);
                    }
                }
            }
        }

        protected void BtnItemCatelog_Click(object sender, EventArgs e)
        {
            if (Request.Params["ReqID"] != null)
            {
                var reqID = Request.Params["ReqID"];
                Response.Redirect("DeptItemCatalog.aspx?ReqID=" + reqID);
            } else
                Response.Redirect("DeptItemCatalog.aspx");            
        }

        protected void BtnSubmit_Click(object sender, EventArgs e)
        {
            if (TableNewReq.Rows.Count <= 1)
            {
                LblErrorText.Text += "Error :: No Item selected !!<br />";
            }
            else
            {
                if (status == "")
                {
                    if (req.ApprovalStatus == "Rejected")
                        req = null;

                    // Save Pending Requisitions
                    if (req != null)
                    {
                        var listSelectedItems = new List<Item>();
                        listSelectedItems = (List<Item>)Session["selectedItems"];
                        var reqDetails = new List<RequisitionDetail>();
                        foreach (var item in listSelectedItems)
                        {
                            var reqDetail = new RequisitionDetail();
                            var itemCode = Controller.selectItemByDescription(item.Description).ItemCode;
                            reqDetail.RequisitionID = req.RequisitionID;
                            reqDetail.ItemCode = itemCode;
                            reqDetail.Quantity = item.CurrentQuantity;
                            reqDetails.Add(reqDetail);
                        }

                        var reqSuccess = Controller.createNewRequisitionWithDetails(emp, req.RequisitionID, textReqComment.Text, reqDetails);
                        //Session["selectedItems"] = null;

                        if (reqSuccess == true)
                        {
                            Response.Write("<script language='javascript'>alert('Edited Requisition is saved successfully !!');</script>");
                            Response.Write("<script language='javascript'>window.location.href = 'DeptRequisitionList.aspx';</script>");
                            //Response.Redirect("~/Views/DeptRequisitionList.aspx");
                        }
                        else
                        {
                            Response.Write("<script language='javascript'>alert('Edited Requisition failed to save !! Try again !!');</script>");
                            Response.Write("<script language='javascript'>window.location.href = 'DeptRequisitionList.aspx';</script>");
                            //Response.Redirect("~/Views/DeptRequisitionList.aspx");
                        }
                    }
                    else
                    {
                        // CreateNewRequisition();

                        var listSelectedItems = new List<Item>();
                        listSelectedItems = (List<Item>)Session["selectedItems"];
                        var reqDetails = new List<RequisitionDetail>();
                        foreach (var item in listSelectedItems)
                        {
                            var reqDetail = new RequisitionDetail();
                            var itemCode = Controller.selectItemByDescription(item.Description).ItemCode;
                            reqDetail.ItemCode = itemCode;
                            reqDetail.Quantity = item.CurrentQuantity;
                            reqDetails.Add(reqDetail);
                        }

                        var reqSuccess = Controller.createNewRequisitionWithDetails(emp, null, textReqComment.Text, reqDetails);
                        //Session["selectedItems"] = null;

                        if (reqSuccess == true)
                        {
                            var toemail = Controller.getDeptHead(emp.DeptCode).Email;
                            var message = "I have raised New requisition for the staionary Items. <br> please check and approve the same <br> <b><a href='http://localhost:5000/Views/DeptAppRequisition.aspx'>Click Here</a></b> to view the Purchase Order";
                            if (Controller.sendEmail(emp.Email, toemail, "New Requisition", message))
                            {
                                Response.Write("<script language='javascript'>alert('New Requisition is created successfully.');</script>");
                                Response.Write("<script language='javascript'>window.location.href = 'DeptRequisitionList.aspx';</script>");
                                //Response.Redirect("~/Views/DeptRequisitionList.aspx");
                            }
                            else
                            {
                                Response.Write("<script language='javascript'>alert('Something went Wrong. Please try again..!.');</script>");
                                Response.Write("<script language='javascript'>window.location.href = 'DeptRequisitionList.aspx';</script>");
                                //Response.Redirect("~/Views/DeptRequisitionList.aspx");
                            }
                        }
                        else
                        {
                            Response.Write("<script language='javascript'>alert('Error :: New Requisition failed !! Network might be down !!');</script>");
                            Response.Write("<script language='javascript'>window.location.href = 'DeptNewRequisition.aspx';</script>");
                            //Response.Redirect("~/Views/DeptRequisitionList.aspx");
                        }
                    }
                }
            }
        }

        //private bool deleteRequisitionDetails(String reqID)
        //{
        //    int rowsAffected = Controller.deleteReqDetailsByReqID(reqID);
        //    return true;
        //}

        private bool insertRequisitionDetails(List<RequisitionDetail> reqDetails)
        {
            foreach (var reqDetail in reqDetails)
            {
                Controller.addNewRequisitionDetails(reqDetail);
            }
            return true;
        }

        //Function relocated to MainController > RequisitionController
        //private bool createNewRequisition()
        //{
        //    int count = 0;

        //    //creating a new requisition
        //    Requisition newRequisition = new Requisition();
        //    newRequisition.RequisitionID = Controller.getNewRequisitionId(emp.EmpID); 
        //    newRequisition.ApprovalStatus = "Pending";
        //    newRequisition.EmpID = emp.EmpID;
        //    Employee deptHead = Controller.selectDeptHead(emp.DeptCode);
        //    if (deptHead != null)
        //    {
        //        newRequisition.AuthorizedBy = deptHead.EmpID;
        //    }
        //    DateTime? reqDate = DateTime.Now;
        //    newRequisition.RequestedDate = reqDate.Value;
        //    newRequisition.Status = "New";
        //    if (textReqComment.Text != "")
        //    {
        //        newRequisition.RequisitionComment = textReqComment.Text;
        //    }

        //    int noOfReqInserted = Controller.addNewRequisition(newRequisition);
        //    if (noOfReqInserted > 0)
        //    {
        //        int noOfReqItems = 0;
        //        foreach (TableRow row in TableRaiseReq.Rows)
        //        {
        //                RequisitionDetail reqDetails = new RequisitionDetail();
        //                //TableRaiseReq.Rows includes Table header row. This is to ignore header row.
        //                if (row.Cells[1].Text.ToString() == "Item Description" )
        //                {
        //                    continue;
        //                }
                        
        //                Item item = Controller.selectItemByDescription(row.Cells[1].Text.ToString());
        //                reqDetails.ItemCode = item.ItemCode;
        //                reqDetails.RequisitionID = newRequisition.RequisitionID;

        //                if (row.FindControl("idQty" + count) != null)
        //                {
        //                    if (row.FindControl("textReqComment") != null)
        //                    {
        //                        reqDetails.Quantity = Convert.ToInt32(((TextBox)row.FindControl("idQty" + count)).Text); 
        //                    }                          
        //                }
        //                noOfReqItems += Controller.addNewRequisitionDetails(reqDetails);
        //                count++; 
        //        }
        //        if (noOfReqItems == TableRaiseReq.Rows.Count - 1)
        //        {
        //            return true; //New Requisition inserted successfully.
        //        }
        //    }
        //    return false; //New Requisition failed.
        //}

        protected void btnDelete_Click(object sender, EventArgs e)
        {
           var btnId =  ((Button)sender).ID;
           for (var i = 1; i < TableNewReq.Rows.Count; i++)
           {
               var controllCollection = TableNewReq.Rows[i].Cells[4].Controls;
               foreach (Control c in controllCollection)
               {
                   if (c.ID == btnId)
                   {
                       var description = TableNewReq.Rows[i].Cells[1].Text;
                       TableNewReq.Rows.RemoveAt(i);
                        if (Session["selectedItems"] != null)
                        {
                            var listSelectedItems = (List<Item>)Session["selectedItems"];
                            foreach (var item in listSelectedItems)
                            {
                                if (item.Description == description)
                                {
                                    listSelectedItems.Remove(item);
                                    break;
                                }
                            }
                            Session["selectedItems"] = listSelectedItems;
                            if (Request.Params["ReqID"] != null)
                            {
                                var reqID = Request.Params["ReqID"];
                                Response.Redirect("~/Views/DeptNewRequisition.aspx?ReqID=" + reqID);
                            }
                            else
                                Response.Redirect("~/Views/DeptNewRequisition.aspx");
                        }
                    }
                }
            }
        }

        protected void txtQty_textChange(object sender, EventArgs e)
        {
            var  qty = 0;
            TextBox tbQty = null;
            String qtyId;
            String itemDescription = null;

            if (((TextBox)sender).Text != "")
            {
                if (int.TryParse(((TextBox)sender).Text, out qty))
                {
                    if (qty <= 0)
                        LblErrorText.Text += "Error :: Quantity should be an Integer > 0 !!<br />";
                }
                else
                    LblErrorText.Text += "Error :: Can't convert Quantity to Integer !!<br />";  

                //try
                //{
                //    qty = Convert.ToInt32(((TextBox)sender).Text);
                //}
                //catch (Exception Ex)
                //{
                //    qty = 0;
                //    //exception = "Error :: Can't convert Quantity to Integer !!";
                //    //Response.Write("<script language='javascript'>alert('Error :: Can't convert Quantity to Integer !!');</script>");
            }

            for (var i = 1; i < TableNewReq.Rows.Count; i++)
            {
                itemDescription = TableNewReq.Rows[i].Cells[1].Text;
                qtyId = "idQty" + (i - 1);
                if (((TextBox)sender).ID == qtyId)
                {
                    tbQty = (TextBox)TableNewReq.Rows[i].Cells[3].FindControl(qtyId);

                    //if (TableRaiseReq.Rows[i].Cells[3].FindControl(qtyId) != null)
                    //    tbQty =  (TextBox) TableRaiseReq.Rows[i].Cells[3].FindControl(qtyId);

                    if (tbQty != null)
                    {
                        try
                        {
                            qty = Convert.ToInt32((tbQty.Text == "" ? "0" : tbQty.Text));
                        }
                        catch (Exception Ex)
                        {
                            qty = 0;
                        }

                        if (Session["selectedItems"] != null)
                        {
                            var listSelectedItem = (List<Item>)Session["selectedItems"];
                            foreach (var item in listSelectedItem)
                            {
                                if (item.Description == itemDescription)
                                {
                                    item.CurrentQuantity = qty;
                                }
                            }
                            Session["selectedItems"] = listSelectedItem;
                        }
                    }
                }
            }
        }

        protected void check_QInteger(Object source, ServerValidateEventArgs args)
        {
            var value = 0;
            var check = (TextBox)source;
            args.IsValid = (check.Text != "" && int.TryParse(check.Text, out value));
        }

        private void displayControls(bool value)
        {
            TableNewReq.Visible = value;
            LblComments.Visible = value;
            BtnSubmit.Visible = value;
            textReqComment.Visible = value;
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