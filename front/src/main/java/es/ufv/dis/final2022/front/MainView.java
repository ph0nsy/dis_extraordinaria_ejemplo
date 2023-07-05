package es.ufv.dis.final2022.front;

import com.google.gson.Gson;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@PageTitle("Examen Final DIS ")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     */
    HorizontalLayout secciones = new HorizontalLayout();
    VerticalLayout option1Cont = new VerticalLayout();
    VerticalLayout option2Cont = new VerticalLayout();
    private final VerticalLayout content;
    /**
     * Clase empleada para la creación de la vista principal de la aplicación.
     */
    public MainView() throws URISyntaxException, IOException, InterruptedException {

        Label Title = new Label("Examen DIS");
        Title.addClassName("title");
        Title.addClassName("centered-content");
        add(Title);


        ServiceAPI serviceAPI = new ServiceAPI();
        List<Producto> productos = serviceAPI.getAllProductos();
        Tab form = new Tab("Formulario");
        Tab listing = new Tab("Listado");

        option2Cont.setJustifyContentMode(JustifyContentMode.CENTER);
        option2Cont.setAlignItems(FlexComponent.Alignment.STRETCH);
        option2Cont.setSpacing(true);
        H1 grid_title = new H1("Productos");
        Grid<Producto> grid = new Grid<>(Producto.class, false);
        grid.addColumn(Producto::getNombre).setHeader("Nombre");
        grid.addColumn(Producto::getCategoria).setHeader("Categoría");
        grid.addColumn(Producto::getPrecio).setHeader("Precio");
        grid.addColumn(Producto::getEan13).setHeader("EAN-13");
        grid.setItems(productos);
        option2Cont.add(grid_title, grid);


        option1Cont.setJustifyContentMode(JustifyContentMode.CENTER);
        option1Cont.setAlignItems(Alignment.CENTER);
        option1Cont.setSpacing(true);
        H1 form_title = new H1("Añada un producto");
        TextField nombre_f = new TextField();
        nombre_f.setLabel("Nombre");
        nombre_f.setPrefixComponent(VaadinIcon.USER.create());
        nombre_f.setErrorMessage("Campo obligatorio");
        TextField categoria_f = new TextField();
        categoria_f.setLabel("Categoría");
        categoria_f.setPrefixComponent(VaadinIcon.TAG.create());
        categoria_f.setErrorMessage("Campo obligatorio");
        IntegerField precio_f = new IntegerField();
        precio_f.setLabel("Precio");
        precio_f.setSuffixComponent(new Div(new Text("€")));
        precio_f.setPrefixComponent(VaadinIcon.MONEY.create());
        precio_f.setErrorMessage("Campo obligatorio");
        TextField ean13_f = new TextField();
        ean13_f.setLabel("Código EAN-13");
        ean13_f.setPrefixComponent(VaadinIcon.CODE.create());
        ean13_f.setErrorMessage("Campo obligatorio");
        Button send_button = new Button("Enviar", e -> {
            try {
                if (nombre_f.getValue() == null || categoria_f.getValue() == null ||  precio_f.getValue() == null ||  ean13_f.getValue() == null){
                    nombre_f.clear();
                    categoria_f.clear();
                    precio_f.clear();
                    ean13_f.clear();
                    Notification notification = Notification.show("Producto Inválido");
                    notification.setPosition(Notification.Position.BOTTOM_START);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
                else{
                    Producto new_prod = serviceAPI.putProducto(nombre_f.getValue(),categoria_f.getValue(), precio_f.getValue(), ean13_f.getValue());
                    if (new_prod != null) {
                        productos.add(new_prod);
                        grid.getDataProvider().refreshAll();
                        nombre_f.clear();
                        categoria_f.clear();
                        precio_f.clear();
                        ean13_f.clear();
                        Notification notification = Notification.show("Producto Introducido");
                        notification.setPosition(Notification.Position.BOTTOM_START);
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    } else {
                        nombre_f.clear();
                        categoria_f.clear();
                        precio_f.clear();
                        ean13_f.clear();
                        Notification notification = Notification.show("Producto Repetido o Inválido");
                        notification.setPosition(Notification.Position.BOTTOM_START);
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        option1Cont.add(form_title, nombre_f, categoria_f, precio_f, ean13_f);
        option1Cont.add(send_button);


        secciones.setJustifyContentMode(JustifyContentMode.EVENLY);
        secciones.setSpacing(true);
        // secciones.add(option1Cont, option2Cont);

        this.setAlignItems(FlexComponent.Alignment.STRETCH);

        Tabs tabs = new Tabs(form, listing);

        content = new VerticalLayout();
        content.setSpacing(false);
        content.add(option1Cont);

        tabs.addSelectedChangeListener(event ->
                {

                content.removeAll();

                if (event.getSelectedTab().equals(listing)) {
                    content.add(option2Cont);
                } else {
                    content.add(option1Cont);
                }
            });
        tabs.addThemeVariants(TabsVariant.LUMO_CENTERED);
        // add(secciones);
        add(tabs, content);

    }
}