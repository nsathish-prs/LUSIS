<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="DeptDetails.aspx.cs" Inherits="LUSIS_WEB_UI.Views.DeptDetails" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

 <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Department Stationary Collection Details
                        </h1>

                        <div class="col-lg-6">
                            

                                 <div class="table-responsive">
                                       <asp:Table ID="TableRaiseReq" class="table table-bordered table-hover table-striped" runat="server">
                                       <asp:TableRow>
                                
                                            <asp:TableCell Width="50%">Current Collection Point:</asp:TableCell>       
                                            <asp:TableCell><asp:Label ID="LblCurrCollPnt" runat="server" Text="Label"></asp:Label></asp:TableCell>

                                      </asp:TableRow>
                     
                                      <asp:TableRow>

                                            <asp:TableCell Width="50%">Current Representative Name:</asp:TableCell>       
                                            <asp:TableCell><asp:Label ID="LblCurrRepName" runat="server" Text="Label"></asp:Label></asp:TableCell>
                                      </asp:TableRow>

                                      </asp:Table>
                                  </div> 

                        </div>

                        <div style="clear:both"></div>
                        
                        <div class="col-lg-6" style="margin-top:1.5%">


                        <div class="table-responsive">
                                       <asp:Table ID="Table1" class="table table-bordered table-hover table-striped" runat="server">
                                       <asp:TableRow>
                                
                                            <asp:TableCell Width="50%">New Collection Point:</asp:TableCell>       
                                                        
                                            <asp:TableCell Width="50%"><asp:DropDownList ID="DDLCollPoints" runat="server" CssClass="form-control"> </asp:DropDownList></asp:TableCell>

                                      </asp:TableRow>
                     
                                      <asp:TableRow>

                                            <asp:TableCell Width="50%">New Representative Name:</asp:TableCell>       
                                                        
                                            <asp:TableCell Width="50%"><asp:DropDownList ID="DDLEmpRepList" runat="server" CssClass="form-control"> </asp:DropDownList></asp:TableCell>
                                      </asp:TableRow>

                                      </asp:Table>
                                  </div> 
                        </div>

                        <div style="clear:both"></div>
                        
                        <div class="col-lg-1" style="margin-top:1%">

                                <asp:Button ID="btnUpdate" runat="server" Text="Update" class="btn btn-primary" 
                                    type="submit" onclick="btnUpdate_Click" />

                        </div>
                    </div>
</div>
</asp:Content>
