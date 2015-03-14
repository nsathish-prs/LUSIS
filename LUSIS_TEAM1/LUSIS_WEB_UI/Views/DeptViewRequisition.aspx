<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="DeptViewRequisition.aspx.cs" Inherits="LUSIS_WEB_UI.Views.DeptViewRequisition" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">


<div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            <asp:Label ID="LblHeadTitle" runat="server" Text="Requisition []"></asp:Label>
                        </h1>
                          <div class="col-lg-3" style="margin-bottom: 1%">
                               Requisition ID :  <asp:Label ID="RequisitionID" runat="server" Text="Label"></asp:Label><br />
                               Employee Name :  <asp:Label ID="EmpName" runat="server" Text="Label"></asp:Label><br /><br />
                               </div>
                              
                      <%--  <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard">Dashboard</i> 
                            </li>
                        </ol>--%>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-8">
                         <div class="panel panel-default">
                        <div class="col-lg-8">
                            <div class="table-responsive">
                              

                                     <asp:Table ID="TableReqDetail" 
                                         class="table table-bordered table-hover table-striped" runat="server">
                                        <asp:TableHeaderRow>
                                            <asp:TableHeaderCell>Item Category</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Item Description</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Unit</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Quantity</asp:TableHeaderCell>
                                            <asp:TableHeaderCell ID="HeadAction">Action</asp:TableHeaderCell>
                                        </asp:TableHeaderRow>
                                        <asp:TableRow>
                                            
                                        </asp:TableRow>
                                        </asp:Table>
                             </div>  
                            </div>
                        </div>
                        <div style="clear:both"></div>
                         <div style="margin-top:1%">
                         
                                    <div class="col-lg-3">
                                    Comments:<br />
                                <asp:TextBox ID="TxtComments" runat="server" TextMode="MultiLine" Columns="30" 
                                            class="form-control" rows="3"></asp:TextBox>
                                </div>
                                
                                <div class="col-lg-3">
                                    <asp:Label ID="LblRComments" runat="server" Text="Rejected Comments:"></asp:Label>
                                    <br />
                                <asp:TextBox ID="TxtRComments" runat="server" TextMode="MultiLine" Columns="30" 
                                        class="form-control" rows="3"></asp:TextBox>
                                </div>
                              
                               <div class="col-lg-4" style="padding-top:1.5%">
                                <asp:Button ID="BtnBack" runat="server" Text="Back" class="btn btn-primary" 
                                       type="submit" onclick="BtnBack_Click" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <asp:Button ID="BtnNewReq" runat="server" Text="Redo Requisition" class="btn btn-primary" 
                                       type="submit" onclick="BtnNewReq_Click" />
                                </div>
                                </div>
                    </div>
                </div>
</asp:Content>
