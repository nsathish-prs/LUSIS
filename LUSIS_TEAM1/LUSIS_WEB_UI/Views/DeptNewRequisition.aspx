<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="DeptNewRequisition.aspx.cs" Inherits="LUSIS_WEB_UI.Views.DeptNewRequisition" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

 <div class="row">
                    <div class="col-lg-8">
                        <h1 class="page-header">
                            <asp:Label ID="LblHeadTitle" runat="server" Text="Requisition [New]"></asp:Label>
                        </h1>
                          <div class="col-lg-3" style="line-height:2em; color:White">
                               <asp:Label ID="LblReqID" runat="server" Text="Requisition ID :"></asp:Label><br />
                               Employee Name :  <asp:Label ID="EmpName" runat="server" Text="Label"></asp:Label>
                               <br />
                               <br />
                               </div>
                               <div class="col-lg-8" style="text-align: right; margin-bottom: 1%">

                                   <asp:Button ID="BtnItemCatelog" runat="server" Text="Item Catalog" 
                                       class="btn btn-primary" onclick="BtnItemCatelog_Click" />
                               </div>
                      <%--  <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard">Dashboard</i> 
                            </li>
                        </ol>--%>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-10">
                         <div class="panel panel-default">
                        <div class="col-lg-8">
                            <div class="table-responsive" style="margin-top:1%">
                              
  
                                    <asp:Table ID="TableNewReq" 
                                        CssClass="table table-bordered table-hover table-striped" runat="server">
                                        <asp:TableHeaderRow>
                                            <asp:TableHeaderCell>Item Category</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Item Description</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Unit</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Quantity</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Action</asp:TableHeaderCell>
                                        </asp:TableHeaderRow>
                                    </asp:Table>

                             </div>  

                            </div>
                        </div>
                        <div style="clear:both"></div>
                         <div style="margin-top:1%">
                         
                                <div class="col-lg-4">
                                    <asp:Label ID="LblComments" runat="server" Text="Comments:"></asp:Label>
                                    <br />
                                    <asp:TextBox ID="textReqComment" runat="server" TextMode="MultiLine" Columns="30" class="form-control" rows="3"></asp:TextBox>
                                </div>
                              
                                <div class="col-lg-4" style="padding-top:1.5%">
                                    <asp:Button ID="BtnSubmit" runat="server" Text="Submit" class="btn btn-primary" 
                                       type="submit" onclick="BtnSubmit_Click" /></div>
                                <div class="col-lg-4" style="padding-top:1.5%">
                                   <asp:Label ID="LblErrorText" style="font:bold" runat="server"></asp:Label>
                                </div>
                                </div>
                    </div>
                </div>
</asp:Content>
