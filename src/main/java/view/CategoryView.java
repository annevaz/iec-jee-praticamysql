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
import model.Category;

@Named
@ViewScoped
public class CategoryView implements Serializable {
    private List<Category> categories;

    private Category selectedCategory;

    @Inject
    private CategoryService categoryService;

    @PostConstruct
    public void init() {
        this.categories = this.categoryService.listing();
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public Category getSelectedCategory() {
        return this.selectedCategory;
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public void newCategory() {
        this.selectedCategory = new Category();
    }

    public void saveCategory() {
        if (this.selectedCategory.getId() == 0) {
            this.categoryService.insert(selectedCategory);

            this.categories.add(this.selectedCategory);

            this.showInfoMessage("Categoria adicionada!");
        } else {
            try {
                this.categoryService.edit(selectedCategory);

                this.showInfoMessage("Categoria editada!");
            } catch (Exception e) {
                this.showErrorMessage("Categoria não editada.", e);
            }
        }

        PrimeFaces.current().executeScript("PF('manageCategoryDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
    }

    private void showInfoMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", message));
    }

    private void showErrorMessage(String message, Exception exception) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", message + " Motivo: " + exception.getMessage()));
    }

    public void deleteCategory() {
        try {
            this.categoryService.delete(selectedCategory.getId());

            this.categories.remove(this.selectedCategory);

            this.selectedCategory = null;

            this.showInfoMessage("Categoria excluída!");
        } catch (Exception e) {
            this.showErrorMessage("Categoria não excluída.", e);
        }

        PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
    }
}
