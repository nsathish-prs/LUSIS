<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="DeptAppRequisitionList.aspx.cs" Inherits="LUSIS_WEB_UI.Views.DeptAppRequisitionList" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <div class="row">
                    <div class="col-lg-8">
                        <h1 class="page-header">
                            Approve Requisition List</h1>
                       <div class="col-lg-12">
                          <div class="table-responsive">
                                    <asp:Table ID="TableAppReqList" 
                                        class="table table-bordered table-hover table-striped" runat="server">
                                        <asp:TableHeaderRow>
                                            <asp:TableHeaderCell>Requisition ID</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Date</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Employee</asp:TableHeaderCell>
                                        </asp:TableHeaderRow>
                                        <asp:TableRow>
                                            
                                        </asp:TableRow>

                                        </asp:Table>
                             
                             </div> 
                             </div> 
                               
                    </div> 
                    </div>


</asp:Content>
