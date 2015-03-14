<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="DeptDelegate.aspx.cs" Inherits="LUSIS_WEB_UI.Views.DeptDelegate" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Delegate Authority
                        </h1>
                       
                    </div>

                    <div class="col-lg-6">
                    <div class="table-responsive">
                    <asp:Table ID="TableRaiseReq" class="table table-bordered table-hover table-striped" runat="server">
                           <asp:TableRow>
                                
                                <asp:TableCell Width="50%">Current Delegate:</asp:TableCell>       
                                <asp:TableCell><asp:Label ID="LblCurrDelegate" runat="server" Text="None"></asp:Label></asp:TableCell>

                          </asp:TableRow>
                     
                          <asp:TableRow>

                                <asp:TableCell Width="50%">Start Date:</asp:TableCell>       
                                <asp:TableCell><asp:Label ID="LblCurrStartDate" runat="server" Text="None"></asp:Label></asp:TableCell>
                          </asp:TableRow>

                          <asp:TableRow>

                                <asp:TableCell Width="50%">End Date:</asp:TableCell>       
                                <asp:TableCell><asp:Label ID="LblCurrEndDate" runat="server" Text="None"></asp:Label></asp:TableCell>
                          </asp:TableRow>

                     </asp:Table>
                     </div>  
                     </div>

                     <div style="clear:both"></div>

                     <div class="col-lg-6" style="margin-top:1%">

                     <div class="table-responsive">
                     <asp:Table ID="Table1" class="table table-bordered table-hover table-striped" runat="server">
                        <asp:TableRow>
                            <asp:TableCell Width="50%">Change Delegate:</asp:TableCell>       
                            <asp:TableCell> <asp:DropDownList ID="DDLEmpList" runat="server" CssClass="form-control"> </asp:DropDownList></asp:TableCell>
                        </asp:TableRow>
                        <asp:TableRow>
                            <asp:TableCell Width="50%">Start Date:</asp:TableCell>       
                            <asp:TableCell><asp:TextBox ID="TxtStartDate" runat="server"></asp:TextBox></asp:TableCell>
                        </asp:TableRow>
                        <asp:TableRow>
                            <asp:TableCell Width="50%">End Date:</asp:TableCell>       
                            <asp:TableCell><asp:TextBox ID="TxtEndDate" runat="server"></asp:TextBox></asp:TableCell>
                        </asp:TableRow>
                     </asp:Table>
                     </div>  
                            
                     </div>
                      <div style="clear:both"></div>
                      <div>
                      <div class="col-lg-1" style="margin-top:1%">

                                <asp:Button ID="BtnSetDelegate" runat="server" Text="Delegate" 
                                    class="btn btn-primary" type="submit" onclick="setDelegate_Click" />
                      </div>
                      <div class="col-lg-1" style="margin-top:1%">
                                <asp:Button ID="BtnCleDelegate" runat="server" Text="Clear Delegate" 
                                    class="btn btn-primary" type="submit" onclick="cleDelegate_Click" />
                      </div>

                      </div>
            </div> 
                      <div style="margin-top:1%">
                          <asp:Label ID="LblErrorText" runat="server" Text=""></asp:Label>
                      </div>
                    

    <script src="../Scripts/bootstrap-datepicker.js"></script>
    <script src="../Scripts/delegate.js"></script>

</asp:Content>
