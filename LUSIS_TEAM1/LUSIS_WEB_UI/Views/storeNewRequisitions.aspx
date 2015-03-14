<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storeNewRequisitions.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storeNewRequisitions" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">
                    <div class="col-lg-7">

                    <h1 class="page-header">
                            New Requisitions
                        </h1>

                         <div class="panel panel-default">
                        
                            <div class="table-responsive">
                              
  
                                    <%--<asp:Table ID="TableStoreNewReq" CssClass="table table-bordered table-hover table-striped" runat="server">
                                        <asp:TableHeaderRow>
                                            <asp:TableHeaderCell>Department Name</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Total Requests</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Representative</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Collection Point</asp:TableHeaderCell>
                                        </asp:TableHeaderRow>
                                    </asp:Table>--%>

                                <asp:GridView ID="gvStoreNewReq" CssClass="table table-bordered table-hover table-striped" runat="server">
                                </asp:GridView>
                             </div>  
                            
                        </div>
                         <div style="margin-top:1%">
                               <div class="col-lg-4" style="padding-top:1.5%">
                                <asp:Button ID="btnGenRetrieval" runat="server" Text="Generate Retrieval List" 
                                       class="btn btn-primary" type="submit" onclick="btnGenRetrieval_Click" />
                                </div>
                                </div>
                    </div>
                </div>


</asp:Content>
