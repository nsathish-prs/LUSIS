using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;
namespace LUSIS_WEB_UI.Views
{
    public partial class storeCheckInventory1 : System.Web.UI.Page
    {
        readonly MainController MainCont = new MainController();
        Employee emp = null;
        private String selected = "";
        private List<String> catList = null;
        private List<Item> itemList = null;
        private string status = "";

        protected void Page_Load(object sender, EventArgs e)
        {
            if (Request.Params["N"] != null)
                Session["Discrepancy"] = null;

            emp = (Employee)Session["Logon"];
            checkAuthorization();
            status = LblErrorText.Text;
            LblErrorText.Text = "";

            if (!IsPostBack)
            {
                if (Session["Discrepancy"] != null)
                {
                    var listDiscrepancy = (List<Discrepancy>)Session["Discrepancy"];
                    foreach (var d in listDiscrepancy)
                        if (d.Quantity == 0)
                            LblErrorText.Text += "Error :: Quantity should be > 0 or < 0 !!<br />";
                    GVTableDiscripancyItems.DataSource = listDiscrepancy;
                    GVTableDiscripancyItems.DataBind();
                }
            }

            catList = MainCont.getItemCategoryList();

            if (IsPostBack == false)
            {
                if (catList != null)
                    catList.Insert(0, "Not Selected");
                else
                {
                    catList = new List<String>();
                    catList.Add("Not Selected");
                }

                DDLCategory.DataSource = catList;
                DDLCategory.DataBind();
            }

            if (DDLCategory.SelectedIndex == -1 || DDLCategory.SelectedIndex == 0)
            {
                var descList = new List<String>();
                descList.Add("Not Selected");
                DDLDescription.DataSource = descList;
                DDLDescription.DataBind();
            }
            else
            {
                selected = DDLDescription.SelectedValue;

                var descList = new List<String>();
                descList.Add("Not Selected");
                itemList = MainCont.getItemListByCategory(DDLCategory.SelectedValue);

                if (itemList != null)
                {
                    foreach (var i in itemList)
                    {
                        descList.Add(i.Description);
                    }
                }
                DDLDescription.DataSource = descList;
                DDLDescription.DataBind();
                for (var x = 0; x < descList.Count; x++)
                {
                    if (descList[x] == selected)
                        DDLDescription.SelectedIndex = x;
                }

                // Load current data into Form controls
                if (DDLDescription.SelectedIndex != -1 && DDLDescription.SelectedIndex != 0)
                {
                    var x = DDLDescription.SelectedIndex - 1;
                    itemDesc.Text = DDLDescription.SelectedValue;
                }
            }
        }

        protected void btnAdd_Click(object sender, EventArgs e)
        {
            if (itemDesc.Text != "")
            {
                var item = new Item();
                item = MainCont.selectItemByDescription(itemDesc.Text);//------1
                if (item != null)
                {
                    List<Discrepancy> listDiscrepancy;
                    var discrepancy = new Discrepancy();
                    if (Session["Discrepancy"] == null)
                    {
                        listDiscrepancy = new List<Discrepancy>();
                        discrepancy.ItemCode = item.ItemCode;
                        discrepancy.EmpID = emp.EmpID;
                        discrepancy.Quantity = 0;
                        discrepancy.Reason = "";
                        DateTime? raisedDate = DateTime.Now;
                        discrepancy.Date = raisedDate.Value;
                        discrepancy.Status = "Pending";
                        discrepancy.Item = item;
                        listDiscrepancy.Add(discrepancy);
                        Session["Discrepancy"] = listDiscrepancy;
                    }
                    else
                    {
                        listDiscrepancy = (List<Discrepancy>)Session["Discrepancy"];
                        foreach (var desc in listDiscrepancy)
                        {
                            if (desc.ItemCode == item.ItemCode)
                            {
                                Response.Write("<script language='javascript'>alert('No such item available.');</script>");
                                Response.Redirect("storeCheckInventory.aspx");
                                break;
                            }
                        }
                        discrepancy.ItemCode = item.ItemCode;
                        discrepancy.EmpID = emp.EmpID;
                        discrepancy.Quantity = 0;
                        discrepancy.Reason = "";
                        DateTime? raisedDate = DateTime.Now;
                        discrepancy.Date = raisedDate.Value;
                        discrepancy.Status = "Pending";
                        discrepancy.Item = item;
                        listDiscrepancy.Add(discrepancy);
                        Session["Discrepancy"] = listDiscrepancy;
                    }
                        Response.Redirect("storeCheckInventory.aspx");
                }
                else
                {
                    Response.Write("<script language='javascript'>alert('No such item available.');</script>");
                    Response.Redirect("storeCheckInventory.aspx");
                }
            }
        }

        protected void submitBtn_Click(object sender, EventArgs e)
        {
            if (Session["Discrepancy"] != null)
            {
                var listDiscrepancy = (List<Discrepancy>)Session["Discrepancy"];

                foreach (var d in listDiscrepancy)
                    if (d.Quantity == 0)
                        LblErrorText.Text += "Error :: Quantity should be > 0 or < 0 !!<br />";

                if (LblErrorText.Text == "" && status == "")
                {

                    var listTempDiscrepancy = new List<Discrepancy>();
                    foreach (var disc in listDiscrepancy)
                    {
                        var tempDisc = new Discrepancy();
                        tempDisc.ItemCode = disc.ItemCode;
                        tempDisc.Quantity = disc.Quantity;
                        tempDisc.Reason = disc.Reason;
                        tempDisc.EmpID = disc.EmpID;

                        listTempDiscrepancy.Add(tempDisc);
                    }
                    var rowAffected = MainCont.addDiscrepancy(listTempDiscrepancy);
                    if (rowAffected > 0)
                    {
                        Session["Discrepancy"] = null;
                        // Response.Redirect("storeHomePage.aspx");
                        Response.Write("<script language='javascript'>alert('New discrepancies added');</script>");
                        Response.Write("<script language='javascript'>window.location.href = 'storeCheckInventory.aspx?N=0';</script>");
                    }
                    else
                    {
                        Response.Write("<script language='javascript'>alert('Adding new discrepancies failed');</script>");
                        Response.Write("<script language='javascript'>window.location.href = 'storeCheckInventory.aspx?N=0';</script>");
                    }
                }
            }
        }

        protected void txtQty_TextChanged(object sender, EventArgs e)
        {
            var currentRow =  (GridViewRow)((TextBox)sender).Parent.Parent;
            var txtQty = (TextBox)currentRow.FindControl("TxtQty");
            var qty = 0;
            if (int.TryParse(txtQty.Text, out qty))
            {
                if (qty == 0)
                    LblErrorText.Text += "Error :: Quantity should be > 0 or < 0 !!<br />";
            }
            else
                LblErrorText.Text += "Error :: Quantity cannot be converted to Integer !!<br />";


            var rowIndex = currentRow.RowIndex;

            List<Discrepancy> listDiscrepancy;
            if (Session["Discrepancy"] != null)
            {
                listDiscrepancy = (List<Discrepancy>) Session["Discrepancy"];
                listDiscrepancy[rowIndex].Quantity = qty;
                Session["Discrepancy"] = listDiscrepancy;
            }
        }
        protected void txtReason_TextChanged(object sender, EventArgs e)
        {
            var currentRow = (GridViewRow)((TextBox)sender).Parent.Parent;
            var txtReason = (TextBox)currentRow.FindControl("TxtReason");
            var reason = txtReason.Text;
            var rowIndex = currentRow.RowIndex;

            List<Discrepancy> listDiscrepancy;
            if (Session["Discrepancy"] != null)
            {
                listDiscrepancy = (List<Discrepancy>)Session["Discrepancy"];
                listDiscrepancy[rowIndex].Reason = reason;
                Session["Discrepancy"] = listDiscrepancy;
            }

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