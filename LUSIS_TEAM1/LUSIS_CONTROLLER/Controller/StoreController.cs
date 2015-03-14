using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using LUSIS_CONTROLLER.Controller;
using LUSIS_EF_FACADE.Facade;
using LUSIS_EF_FACADE;

namespace LUSIS_CONTROLLER.Controller
{
    public class StoreController 
    {
        EF_Facade facade;

        public StoreController()
        {
            facade = new EF_Facade();
        }

        // StoreController (Return List<Supplier> = List of Supplier Or Null) // Benny
        public List<Supplier> getSupplierList()
        {
            return facade.getSupplierList();
        }

        // StoreController (Return Supplier By ID Or Null) // Benny
        public Supplier getSupplierByID(String supID)
        {
            return facade.getSupplierByID(supID);
        }

        // StoreController (Return Supplier By ID Or Null) // Benny
        public List<ItemSupplierDetail> getItemSupplierListBySupID(String supID)
        {
            return facade.getItemSupplierListBySupID(supID);
        }

        // StoreController // Delete Supplier if no ItemSupplier records. // Benny
        public bool delSupplier(String supID)
        {
            return facade.delSupplier(supID);
        }

        // StoreController // Insert new Supplier if not exist. // Benny
        public bool addSupplier(Supplier sup)
        {
            return facade.addSupplier(sup);
        }

        // StoreController // Set Supplier if Exist. // Benny
        public bool setSupplier(Supplier sup)
        {
            return facade.setSupplier(sup);
        }

        // StoreController // Get ItemCategoryList. // Benny
        public List<String> getItemCategoryList()
        {
            return facade.getItemCategoryList();
        }

        // StoreController // Get ItemListByCategory. // Benny
        public List<Item> getItemListByCategory(String cat)
        {
            return facade.getItemListByCategory(cat);
        }

        // StoreController // Insert New Item. // Benny
        public bool addItem(Item item)
        {
            return facade.addItem(item);
        }

        // StoreController // Set setItem. // Benny
        public bool setItem(Item item)
        {
            return facade.setItem(item);
        }

        // StoreController // Get getItemSupplierList. // Benny
        public List<ItemSupplierDetail> getItemSupplierList(String itemCode)
        {
            return facade.getItemSupplierList(itemCode);
        }

        // StoreController // Set setItemSupplierList. // Benny
        public bool setItemSupplierList(List<ItemSupplierDetail> list, String ICode)
        {
            return facade.setItemSupplierList(list, ICode);
        }

        public int getNewRequisitionCount()
        {
            return facade.getNewRequisitionCount();
        }
    }
}
