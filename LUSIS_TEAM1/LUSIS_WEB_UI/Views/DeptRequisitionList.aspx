<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="DeptRequisitionList.aspx.cs" Inherits="LUSIS_WEB_UI.Views.DeptRequisitionList" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">
                    <div class="col-lg-8">
                        <h1 class="page-header">
                            <asp:Label ID="LblHeadTitle" runat="server" Text="Requisition List []"></asp:Label>
                        </h1>
                          
                      <%--  <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard">Dashboard</i> 
                            </li>
                        </ol>--%>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-12">

                    <div class="col-lg-4" style="color:White"> 
                        <asp:RadioButtonList ID="RadioBtnListReq" CssClass="radio" runat="server" AutoPostBack="True" 
                            RepeatDirection="Horizontal">
                               <asp:ListItem Value="MyRequisitions">My Requisitions</asp:ListItem>
                               <asp:ListItem Value="AllRequisitions">All Requisitions</asp:ListItem>
                        </asp:RadioButtonList>
                        </div>
                        <%--<div class="col-lg-2"> All requests</div>--%>

                    <div style="clear:both"></div>

                    <div class="col-lg-8">
                         <div class="panel panel-default">
                        
                            <div class="table-responsive">
                              

                                     <asp:Table ID="TableReqList" 
                                        CssClass="table table-bordered table-hover table-striped" runat="server">
                                        <asp:TableHeaderRow>
                                            <asp:TableHeaderCell>Requisition ID</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Date</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Employee</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Status</asp:TableHeaderCell>
                                        </asp:TableHeaderRow>
                                        <asp:TableRow>
                                            
                                        </asp:TableRow>
                                        </asp:Table>
                             </div>  
                            
                        </div>
                        </div>
                    </div>
                </div>


</asp:Content>
