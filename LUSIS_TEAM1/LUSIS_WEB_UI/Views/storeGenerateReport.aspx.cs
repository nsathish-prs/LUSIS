using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE;
using LUSIS_WEB_UI.Reports_Dataset.LUSIS_DatasetTableAdapters;
using LUSIS_WEB_UI.Reports_Dataset;

namespace LUSIS_WEB_UI.Views
{
    public partial class storeGenerateReport : System.Web.UI.Page
    {
        readonly MainController mc = new MainController();
        string selection = "";
        List<string> repItem;
        List<string> selMonth;
        string selm = "";
        string deptselected = "";
        List<string> departmentList;
        string reportType = "";
        string repBased = "";
        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            MultiView1.ActiveViewIndex = 0;
            if (!IsPostBack)
            {
                fillpage();
                filldeptList();
                filMonth();
            }

        }

        protected void ReportType_Change(object sender, EventArgs e)
        {
            selection = repType.SelectedItem.ToString();
            if (selection == "Trend Analysis")
            {
                Label1.Visible = true;
                deptList.Visible = true;
            }
            else
            {
                Label1.Visible = false;
                deptList.Visible = false;
            }
            if (selection == "Purchase Order")
            {
            }
        }

        public void fillpage()
        {
            var list = mc.getdistItemList();
            var listItems = new List<string>();
            var iCategory = new List<string>();
            foreach (var item in list)
            {
                listItems.Add(item.Category);
            }

            iCategory = listItems.Distinct().ToList();
            iCategory.Add(" Select ");
            foreach (var items in iCategory)
            {
                iCategoryList.Items.Add(items);
            }

            repBasedOn.Items.Add("Amount");
            repBasedOn.Items.Add("Quantity");

            repType.Items.Add("Purchase Order");
            repType.Items.Add("Trend Analysis");

        }

        public void filMonth()
        {
            var months = System.Globalization.CultureInfo.CurrentCulture.DateTimeFormat.MonthNames;
            foreach (var s in months)
            {
                monthName.Items.Add(s);
            }
        }

        public void filldeptList()
        {
            //deptList.Items.Add("All");
            var deptNames = mc.getdeptList();
            foreach (var s in deptNames)
            {
                deptList.Items.Add(s);
            }
        }

        protected void iCategoryList_SelectedIndexChanged(object sender, EventArgs e)
        {
            iList.Items.Clear();
            CategoryLabel.Visible = true;
            var iSelected = iCategoryList.SelectedItem.ToString();
            if (iSelected != "All")
            {
                var iDesc = mc.getItemsbyDesc(iSelected);
                foreach (var s in iDesc)
                {
                    iList.Items.Add(s);
                }
                iList.Visible = true;
            }
            else
            {
                iList.Visible = false;
            }
        }

        protected void btnGenerate_Click(object sender, EventArgs e)
        {
            repItem = new List<string>();
            
            if (iCategoryList.SelectedItem.ToString() != "All")
            {
                var count = 0;
                foreach (ListItem li in iList.Items)
                {
                    if (li.Selected)
                    {
                        count++;
                        repItem.Add(mc.getItemByDescr(li.Text).ToString());
                    }
                }
                if (count < 3)
                {
                    Response.Write("<script language='javascript'>alert('Select three values for Items');</script>");
                    return;
                }
            }
           
            repBased = repBasedOn.SelectedItem.ToString();
            reportType = repType.SelectedItem.ToString();
            selMonth = new List<string>();

            var count1 = 0;
            foreach (ListItem li in monthName.Items)
            {
                if (li.Selected)
                {
                    count1++;
                    if (li.Text.Equals("All"))
                    {
                        selm = "All";
                    }
                    else
                    {
                        selMonth.Add(li.ToString());
                    }
                   
                }
            }
            if (count1 < 3)
            {
                Response.Write("<script language='javascript'>alert('Select three values for Month');</script>");
                return;
            } 
            if (repType.SelectedItem.ToString().Equals("Trend Analysis"))
            {
                departmentList = new List<string>();
                foreach (ListItem li in deptList.Items)
                {
                    if (li.Selected)
                    {
                        if (li.Text.ToString() != "All")
                        {
                            departmentList.Add(mc.getDeptCodewithName(li.Text));
                        }
                        else
                        {
                            deptselected = "All";
                        }
                    }
                }
            }
            fillReport();
        }

        public void fillReport()
        {
            var ds = new LUSIS_Dataset();

            if (reportType.Equals("Purchase Order"))
            {
                //rep based on quantity + (3 items + 3 months)
                if (repBased == "Quantity" && selMonth[0] != null)
                {
                        if (selm != "All")
                        {
                            var da = new ItemsMonthQuantityTableAdapter();
                            da.Fill(ds.ItemsMonthQuantity, getmonthno(selMonth[0].ToString()), getmonthno(selMonth[1].ToString()), getmonthno(selMonth[2].ToString()), repItem[0], repItem[1], repItem[2]);
                            var ir = new ItemsMonthQuantity();
                            ir.SetDataSource(ds);
                            CrystalReportViewer1.ReportSource = ir;
                            MultiView1.ActiveViewIndex = 1;
                        }
                        else
                        {
                            Response.Write("<script>function(){alert('Please select Months');}</script>");
                        }
                }
                else
                {
                    //rep based on Amount (3 items + 3 months)
                    if (repItem.Count >0 && repItem.Count <4)
                    {
                        if (selm != "All")
                        {
                            var da = new ItemsMonthAmountTableAdapter();
                            da.Fill(ds.ItemsMonthAmount, getmonthno(selMonth[0].ToString()), getmonthno(selMonth[1].ToString()), getmonthno(selMonth[2].ToString()), repItem[0], repItem[1], repItem[2]);
                            var ir = new ItemsMonthAmount();
                            ir.SetDataSource(ds);
                            CrystalReportViewer1.ReportSource = ir;
                            MultiView1.ActiveViewIndex = 1;
                        }
                        else
                        {
                            Response.Write("<script>function(){alert('Please select Months');}</script>");
                        }
                    }
                    else
                    {
                        Response.Write("<script>function(){alert('Please select Items');}</script>");
                    }
                }
            }
            else if (reportType.Equals("Trend Analysis"))
            {
                if (departmentList.Count >0 && departmentList.Count <4 && repItem.Count != 0 && repItem.Count>1 && selMonth[0] != null)
                {
                    if (repBased == "Amount")
                    {
                        var da = new ItemDepartmentAmountTableAdapter();
                        da.Fill(ds.ItemDepartmentAmount, departmentList[0], departmentList[1], departmentList[2], repItem[0], repItem[1], repItem[2]);
                        var ir = new ItemDepartmentAmount();
                        ir.SetDataSource(ds);
                        CrystalReportViewer1.ReportSource = ir;
                        MultiView1.ActiveViewIndex = 1;
                    }
                    else
                    {
                        var da = new ItemDepartmentQuantityTableAdapter();
                        da.Fill(ds.ItemDepartmentQuantity, departmentList[0], departmentList[1], departmentList[2], repItem[0], repItem[1], repItem[2]);
                        var ir = new ItemDepartmentQuantity();
                        ir.SetDataSource(ds);
                        CrystalReportViewer1.ReportSource = ir;
                        MultiView1.ActiveViewIndex = 1;
                    }
                }
                else if (departmentList[0] != null && selMonth[0] != null && repBased == "Amount" && repItem.Count == 0)
                {
                    var da = new DepartmentAmountTableAdapter();
                    da.Fill(ds.DepartmentAmount, departmentList[0], departmentList[1], departmentList[2], getmonthno(selMonth[0].ToString()), getmonthno(selMonth[1].ToString()), getmonthno(selMonth[2].ToString()));
                    var ir = new DepartmentAmount();
                    ir.SetDataSource(ds);
                    CrystalReportViewer1.ReportSource = ir;
                    MultiView1.ActiveViewIndex = 1;
                }
                else if (departmentList[0] != null && selMonth[0] != null && repBased == "Amount" && repItem.Count == 1)
                {
                    var da = new _1ItemDepartmentAmountTableAdapter();
                    da.Fill(ds._1ItemDepartmentAmount,departmentList[0], departmentList[1], departmentList[2], repItem[0], getmonthno(selMonth[0].ToString()), getmonthno(selMonth[1].ToString()), getmonthno(selMonth[2].ToString()));
                    var ir = new ItemDepartmentAmount1();
                    ir.SetDataSource(ds);
                    CrystalReportViewer1.ReportSource = ir;
                    MultiView1.ActiveViewIndex = 1;
                }
                else if (departmentList[0] != null && selMonth[0] != null && repBased == "Quantity" && repItem.Count == 1)
                {
                    var da = new _1ItemDepartmentQuantityTableAdapter();
                    da.Fill(ds._1ItemDepartmentQuantity, departmentList[0], departmentList[1], departmentList[2], repItem[0], getmonthno(selMonth[0].ToString()), getmonthno(selMonth[1].ToString()), getmonthno(selMonth[2].ToString()));
                    var ir = new ItemDepartmentQuantity1();
                    ir.SetDataSource(ds);
                    CrystalReportViewer1.ReportSource = ir;
                    MultiView1.ActiveViewIndex = 1;
                }
                else
                {
                    Response.Write("<script>function(){alert('Please select Proper Values');}</script>");
                }
            }
            else
            {
                Response.Write("<script>function(){alert('Please select Proper values');}</script>");
            }

        }


        public int getmonthno(string month)
        {
            switch (month)
            {
                case "January": return 1; break;
                case "February": return 2; break;
                case "March": return 3; break;
                case "April": return 4; break;
                case "May": return 5; break;
                case "June": return 6; break;
                case "July": return 7; break;
                case "August": return 8; break;
                case "September": return 9; break;
                case "October": return 10; break;
                case "November": return 11; break;
                default: return 12; break;
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