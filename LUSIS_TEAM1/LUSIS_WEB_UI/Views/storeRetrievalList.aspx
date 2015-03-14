<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storeRetrievalList.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storeRetrievalList" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
<div class="row">
            <asp:MultiView ID="MultiView1" runat="server">
                <asp:View ID="View1" runat="server">
                    <div class="col-lg-8">
                    <h1 class="page-header">
                            Retrieval List</h1>
                        <div class="col-lg-12">
                        <div class="panel panel-default">
                         
                            <asp:GridView ID="GridView1" 
                                 CssClass="table table-bordered table-hover table-striped" runat="server" 
                                 AutoGenerateColumns="False" >
                                 <Columns>
                                     <asp:BoundField DataField="Item.BinId" HeaderText="Bin Id " />
                                     <asp:BoundField DataField="Item.Description" HeaderText="Description" />
                                     <asp:BoundField DataField="Item.CurrentQuantity" 
                                         HeaderText="Available Quantity" /> 
                                     <asp:BoundField DataField="RequestedQty" HeaderText="Requested Quantity" />
                                     <asp:BoundField DataField="RetrievedQty" HeaderText="Retrieved Quantity" />
                                 </Columns>
                             </asp:GridView>
                             <h4><asp:Label ID="LblNote" runat="server" ForeColor="White" Text="" Visible="False"></asp:Label></h4>
                        </div>
                        </div>
                        <div style="clear:both"></div>
                        <div style="margin-top:1%">
                                <div class="col-lg-4" style="padding-top:1.5%">
                                <asp:Button ID="genDisbursementBtn" runat="server" Text="Generate Disbursement List" 
                                       class="btn btn-primary" type="submit" onclick="genDisbursement_Click" />
                                </div>
                                </div>
                    </div>
                </asp:View>
                <asp:View ID="View2" runat="server">
                <div class="col-lg-10">
                    <h1 class="page-header">
                            Generate Disbursement
                    </h1>
                         <div style="margin-bottom:1.5%">
                               <div class="col-lg-4" style="margin-bottom:1.5%">
                                   <asp:Button ID="BtnUpdate" class="btn btn-primary" runat="server" 
                                       Text="Update Data" onclick="BtnUpdate_Click" causesValidation="true" ValidationGroup="QuantityValidation"/>
                                <asp:Button ID="btnSave" runat="server" Text="Finalise Disbursement " 
                                       class="btn btn-primary" type="submit" onclick="btnSave_Click" causesValidation="true" ValidationGroup="QuantityValidation"/>
                                </div>
                                </div>
                                <div style="clear:both"></div>
                            <div class="col-lg-8">
                            <div class="panel panel-default">
                                <div class="table-responsive">
                                    <asp:Table ID="TableDisbList"  CssClass="table table-bordered table-hover table-striped" runat="server">
                                    </asp:Table>
                                </div>  
                            </div>
                            </div>
                        <div style="clear:both">
                            <table class="nav-justified">
                                <tr><td>&nbsp;</td></tr>
                            </table>
                        </div>
                        </div>       
                </asp:View>
             </asp:MultiView>
             </div>
</asp:Content>
