<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="DeptReports.aspx.cs" Inherits="LUSIS_WEB_UI.Views.DeptReports" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

 <div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">
            Generate Reports
        </h1>
                       
    </div>

    <div class="col-lg-12">
        

        
            <asp:MultiView ID="MultiView1" runat="server">
                <asp:View ID="View1" runat="server">
                <div class="col-lg-10">
                    <div class="col-lg-6"><h4>Generate Monthly Report</h4></div>
                <div class="col-lg-3">
                    <asp:Button ID="mntReport" runat="server" Text="Compare Monthly Reports  For Year" 
                         class="btn btn-primary" type="submit" onclick="mntReport_Click" />
                     </div>
                </div>
                    <div class="col-lg-6"> 
                    

            <div class="table-responsive">

                    <asp:Table ID="TableRaiseReq" class="table table-bordered table-hover table-striped" runat="server">
                    
                    <asp:TableRow>

                        <asp:TableCell>Report Type</asp:TableCell>
                        <asp:TableCell>
                            <asp:DropDownList ID="empList" runat="server" CssClass="form-control"> </asp:DropDownList>
                        </asp:TableCell>

                    </asp:TableRow>

                    <asp:TableRow>

                        <asp:TableCell>Report Based On</asp:TableCell>
                        <asp:TableCell>
                                <asp:DropDownList ID="repBasedonList" runat="server" CssClass="form-control"> </asp:DropDownList>
                        </asp:TableCell>

                    </asp:TableRow>

                    <asp:TableRow>

                        <asp:TableCell>Report Month</asp:TableCell>
                        <asp:TableCell>
                            <asp:DropDownList ID="mthList" runat="server" CssClass="form-control"> </asp:DropDownList>
                        </asp:TableCell>

                    </asp:TableRow>

                    <asp:TableRow>

                        <asp:TableCell>Report Year</asp:TableCell>
                        <asp:TableCell>
                            <asp:DropDownList ID="yearList" runat="server" CssClass="form-control"> </asp:DropDownList>
                        </asp:TableCell>

                    </asp:TableRow>
                    </asp:Table>

                    <div class="col-lg-1" style="margin-top:1%">

                                <asp:Button ID="genReport" runat="server" Text="Generate Report" class="btn btn-primary" type="submit" />

                      </div>

                </div>  

    </div>

                </asp:View>
                <asp:View ID="View2" runat="server">
                    
                    
                    <div class="col-lg-10">
                    <div class="col-lg-6"><h4>Compare Monthly Reports  For Year</h4></div>
                              <div class="col-lg-3">
                                    <asp:Button ID="mthReportgen" runat="server" Text="Generate Monthly Report" 
                                                class="btn btn-primary" type="submit" onclick="mthReportgen_Click" />
                              </div>
                    </div>
                    
                    
                    <div class="col-lg-6"> 
                    <div class="table-responsive">

                    <asp:Table ID="Table1" class="table table-bordered table-hover table-striped" runat="server">
                    
                    <asp:TableRow>
                        <asp:TableCell>Report Type</asp:TableCell>
                        <asp:TableCell>Total Charge Back</asp:TableCell>

                    </asp:TableRow>

                    <asp:TableRow>
 
                        <asp:TableCell>Report Based On</asp:TableCell>
                        <asp:TableCell>Months</asp:TableCell>

                    </asp:TableRow>

                    <asp:TableRow>

                        <asp:TableCell>Report Months</asp:TableCell>
                        <asp:TableCell>
                            <asp:ListBox ID="mthLsist" runat="server"></asp:ListBox>
                        </asp:TableCell>

                    </asp:TableRow>

                    <asp:TableRow>

                        <asp:TableCell>Report Year</asp:TableCell>
                        <asp:TableCell>
                            <asp:DropDownList ID="yearsList" runat="server" CssClass="form-control"> </asp:DropDownList>
                        </asp:TableCell>

                    </asp:TableRow>
                    </asp:Table>

                    <div class="col-lg-1" style="margin-top:1%">

                                <asp:Button ID="sgenReport" runat="server" Text="Generate Report" class="btn btn-primary" type="submit" />

                    </div>

                </div>  

    </div>

                </asp:View>
            </asp:MultiView>
        </div>

 </div>

</asp:Content>
