using System;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;

namespace LUSIS_WEB_UI.Views
{
    public partial class storeSupplier : System.Web.UI.Page
    {
        readonly MainController MainCont = new MainController();
        Supplier sup = null;
        String supID = null;

        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            if (IsPostBack == false)
                LblErrorText.Text = "";
            // Load storeSupplier.aspx in EditSupplier mode.
            if (Request.Params["SupID"] != null)
            {
                if (IsPostBack == false)
                {
                    supID = Request.Params["SupID"].ToString();
                    sup = MainCont.GetSupplierById(supID);
                    if (sup != null)
                    {
                        // Fill Table
                        TxtSupCode.Text = sup.SupplierID;
                        TxtSupCode.ReadOnly = true;
                        TxtSupName.Text = sup.Name;
                        TxtSupAddress.Text = sup.Address;
                        TxtSupFax.Text = sup.FaxNumber;
                        TxtConName.Text = sup.ContactName;
                        TxtConNum.Text = sup.PhoneNumber;
                    }
                    else
                        Response.Redirect("~/Views/storeSupplier.aspx");
                }
            }
            // Load storeSupplier.asp in NewSupplier mode.
            else
            {
                LblHeadTitle.Text = "New Supplier Details";
                BtnDone.Text = "Save Supplier";
            }
        }

        protected void BtnDone_Click(object sender, EventArgs e)
        {
            var control = (Button)sender;
            sup = new Supplier();

            sup.SupplierID = TxtSupCode.Text;
            sup.Name = TxtSupName.Text;
            sup.Address = TxtSupAddress.Text;
            sup.FaxNumber = TxtSupFax.Text;
            sup.ContactName = TxtConName.Text;
            sup.PhoneNumber = TxtConNum.Text;

            if (sup.SupplierID == "")
                LblErrorText.Text += "Error :: Supplier Code cannot be Empty !!<br />";
            if (sup.Name == "")
                LblErrorText.Text += "Error :: Supplier Name cannot be Empty !!<br />";
            if (sup.Address == "")
                LblErrorText.Text += "Error :: Address cannot be Empty !!<br />";
            if (sup.FaxNumber == "")
                ;
            if (sup.ContactName == "")
                LblErrorText.Text += "Error :: Contact Name cannot be Empty !!<br />";
            if (sup.PhoneNumber == "")
                LblErrorText.Text += "Error :: Contact Number cannot be Empty !!<br />";

            if (LblErrorText.Text == "")
            {
                if (control.Text == "Save Supplier")
                {
                    if (MainCont.addSupplier(sup))
                    {
                        Response.Write("<script language='javascript'>alert('Success :: Supplier Details Inserted.');</script>");
                        Response.Write("<script language='javascript'>window.location.href = 'storeMaintainSuppliers.aspx';</script>");
                    }
                    else
                        Response.Write("<script language='javascript'>alert('Error :: Fail to Insert New Supplier.');</script>");
                }
                else if (control.Text == "Update")
                {
                    if (MainCont.setSupplier(sup))
                    {
                        Response.Write("<script language='javascript'>alert('Success :: Supplier Details Updated.');</script>");
                        //Response.Redirect("~/Views/storeSupplier.aspx?SupID=" + sup.SupplierID);
                    }
                    else
                        Response.Write("<script language='javascript'>alert('Error :: Record do not exist.');</script>");
                }
                else
                    Response.Write("<script language='javascript'>alert('Error :: Unknown.');</script>");
            }
            else
                LblErrorText.Text += "Error :: Failed to Save Supplier Details !!<br />";
        }

        protected void BtnCancel_Click(object sender, EventArgs e)
        {
            Response.Redirect("~/Views/storeMaintainSuppliers.aspx");
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