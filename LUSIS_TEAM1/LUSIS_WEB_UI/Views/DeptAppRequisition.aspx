<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="DeptAppRequisition.aspx.cs" Inherits="LUSIS_WEB_UI.Views.DeptAppRequisition" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Approve Requisition</h1>

                          <div class="col-lg-3" style="margin-bottom: 1%">
                               Requisition ID :  <asp:Label ID="LblRequisitionID" runat="server" Text="Label"></asp:Label><br />
                               Employee Name :  <asp:Label ID="LblEmpName" runat="server" Text="Label"></asp:Label><br />
                               </div>

                               <div style="clear:both"></div>
                       <div class="col-lg-8">
                          <div class="table-responsive">
                                    <asp:Table ID="TableAppReq" 
                                        class="table table-bordered table-hover table-striped" runat="server">
                                        <asp:TableHeaderRow>
                                            <asp:TableHeaderCell>Item Category</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Item Description</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Unit</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Quantity</asp:TableHeaderCell>
                                        </asp:TableHeaderRow>
                                        <asp:TableRow>
                                            
                                        </asp:TableRow>

                                        </asp:Table>
                             
                             </div>  
                             </div>
                             <div style="clear:both"></div>
                             <div style="margin-top:1%; margin-bottom:1%">
                         
                                <div class="col-lg-3">
                                    Comments:<br />
                                <asp:TextBox ID="TxtComments" runat="server" TextMode="MultiLine" Columns="30" 
                                            class="form-control" rows="3"></asp:TextBox>
                                </div>
                                
                                <div class="col-lg-3">
                                    Rejected Comments:<br />
                                <asp:TextBox ID="TxtRComments" runat="server" TextMode="MultiLine" Columns="30" 
                                        class="form-control" rows="3"></asp:TextBox>
                                </div>
                              
                               
                             </div>
                             </div>
                             <div style="clear:both"></div>

                                <div>
                                <div class="col-lg-1" style="padding-top:1.5%; width: auto;">
                                <asp:Button ID="BtnApprove" runat="server" Text="Approve" class="btn btn-primary" 
                                        type="submit" onclick="BtnApprove_Click" />&nbsp;&nbsp;&nbsp;&nbsp;
                                </div>
                                <div class="col-lg-1" style="padding-top:1.5%; width: auto;">
                                <asp:Button ID="BtnReject" runat="server" Text="Reject" class="btn btn-primary" 
                                        type="submit" onclick="BtnReject_Click" />&nbsp;&nbsp;&nbsp;&nbsp;
                                </div>
                                <div class="col-lg-1" style="padding-top:1.5%; width: auto;">
                                <asp:Button ID="BtnBack" runat="server" Text="Back" class="btn btn-primary" 
                                        type="submit" onclick="BtnBack_Click" />&nbsp;&nbsp;&nbsp;&nbsp;
                                </div>
                                </div>
 
                    </div> 
</asp:Content>
