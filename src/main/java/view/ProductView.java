package view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import business.CategoryService;
import business.ProductService;
import model.Category;
import model.Product;

@Named
@ViewScoped
public class ProductView implements Serializable {
    private List<Product> products;

    private Product selectedProduct;

    private List<Category> categories;

    private Category searchCategory;

    @Inject
    private ProductService productService;

    @Inject
    private CategoryService categoryService;

    @PostConstruct
    public void init() {
        String categoriaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
            .get("category-id");

        if (categoriaId != null) {
            try {
                this.searchCategory = this.categoryService.byId(Integer.parseInt(categoriaId));
            } catch (NumberFormatException e) {
                this.showErrorMessage("O identificador da categoria não corresponde ao formato esperado!", e);
            } catch (Exception e) {
                this.showErrorMessage("Não foi possível converter o identificador da categoria para o corresponde ao " +
                    "formato esperado!", e);
            }

            this.searchProductByCategory();

            this.categories = this.categoryService.listing();
        } else {
            this.products = this.productService.listing();

            this.categories = this.categoryService.listing();
            this.categories.add(0, new Category(0, "Todos"));

            this.searchCategory = new Category();
        }
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public Product getSelectedProduct() {
        return this.selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public Category getSearchCategory() {
        return this.searchCategory;
    }

    public void setSearchCategory(Category category) {
        this.searchCategory = category;
    }

    public void newProduct() {
        this.selectedProduct = new Product();
        this.selectedProduct.setCategory(new Category());
    }

    public void saveProduct() {
        this.updateCategory();

        if (this.selectedProduct.getId() == 0) {
            this.productService.insert(selectedProduct);

            this.products.add(this.selectedProduct);

            this.showInfoMessage("Produto adicionado!");
        } else {
            try {
                this.productService.edit(selectedProduct);

                this.showInfoMessage("Produto editado!");
            } catch (Exception e) {
                this.showErrorMessage("Produto não editado.", e);
            }
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    private void updateCategory() {
        try {
            selectedProduct.setCategory(this.categoryService.byId(selectedProduct.getCategory().getId()));
        } catch (Exception e) {
            this.showErrorMessage("Categoria do produto não atualizada.", e);
        }
    }

    private void showInfoMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", message));
    }

    private void showErrorMessage(String message, Exception exception) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", message + " Motivo: " + exception.getMessage()));
    }

    public void deleteProduct() {
        try {
            this.productService.delete(selectedProduct.getId());

            this.products.remove(this.selectedProduct);

            this.selectedProduct = null;

            this.showInfoMessage("Produto excluído!");
        } catch (Exception e) {
            this.showErrorMessage("Produto não excluído.", e);
        }

        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public void searchProductByCategory() {
        if (this.products != null) {
            this.products.clear();
        }

        try {
            if (this.searchCategory.getId() == 0) {
                this.products = this.productService.listing();
            } else {
                this.products = this.productService.byCategory(this.searchCategory);
            }
        } catch (Exception e) {
            this.showErrorMessage("Não foram encontrados os produtos da categoria informada.", e);
        }
    }
}
