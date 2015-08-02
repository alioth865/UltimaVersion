package com.example.alioth.ultimaversion.Todo.Util;

import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.util.Log;
import android.util.Xml;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Modelo.Categoria;
import com.example.alioth.ultimaversion.Todo.Modelo.Cesta;
import com.example.alioth.ultimaversion.Todo.Modelo.Color;
import com.example.alioth.ultimaversion.Todo.Modelo.Combinacion;
import com.example.alioth.ultimaversion.Todo.Modelo.Direccion;
import com.example.alioth.ultimaversion.Todo.Modelo.OpcionesProductos;
import com.example.alioth.ultimaversion.Todo.Modelo.Pedido;
import com.example.alioth.ultimaversion.Todo.Modelo.Producto;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;


/**
 * Created by Alioth on 15/06/2015.
 */
public class Utilidades {
    public static final int idFragmentCategoria=1;
    public static final int idFragmentProducto=2;
    public static final int idFragmentProductoEspecifico=3;
    public static final int idFragmentClientes=4;
    public static final int idFragmentClienteEspecifico=5;
    public static final int idFragmentCrearPedido=6;
    public static final int idFragmentBuscarProducto=7;
    public static final int idFragmentElegirCombinacion=8;
    public static final int idModificarCesta =9;



    public static InputStream peticionHttp(String usuario, String contraseña, String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection uc = (HttpURLConnection )url.openConnection();
        String userpass = usuario + ":" + contraseña;
        String basicAuth = "Basic " + new String(Base64.encodeBase64(userpass.getBytes()));
        uc.setRequestProperty ("Authorization", basicAuth);
        InputStream inputStream=uc.getInputStream();
        //uc.disconnect();
        return inputStream;
    }

    public static String peticionPostHttp(String usuario, String contraseña, String urlString, String xml) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();

        String userpass = usuario + ":" + contraseña;
        String basicAuth = "Basic " + new String(Base64.encodeBase64(userpass.getBytes()));
        HttpPost httppost = new HttpPost(urlString);
        httppost.setHeader("Authorization", basicAuth);
        StringEntity se = new StringEntity( xml, HTTP.UTF_8);
        se.setContentType("text/xml");
        httppost.setEntity(se);

        HttpResponse httpresponse = httpclient.execute(httppost);
        HttpEntity resEntity = httpresponse.getEntity();
        return EntityUtils.toString(resEntity);
    }


    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }


    public static Map<String, String> Config(Resources r){
        Map<String,String> m=new HashMap<>();
        try
        {
            InputStreamReader isr = new InputStreamReader(r.openRawResource(R.raw.info));
            BufferedReader br = new BufferedReader(isr);
            String linea;
            while ((linea=br.readLine())!=null){
                String[] parts=linea.split(":");
                m.put(parts[0],parts[1]);
            }
            br.close();
            isr.close();
        }
        catch (IOException ex){
            Log.e("Ficheros", "Error al leer fichero desde recurso raw");
            return null;
        }
        return m;
    }

    public static List<OpcionesProductos> buscarOpciones(List<Combinacion> combinacions, OpcionesProductos opcionesProductos){
        //Esto se va a usar cada vez que se cambie el spiner con las opciones del producto
        List<OpcionesProductos> retorno=new LinkedList<>();
        for(Combinacion tmp: combinacions){
            List<OpcionesProductos> opciones=tmp.getOpcionesProductosList();
            int pos=opciones.indexOf(opcionesProductos);
            if(opciones.contains(opcionesProductos)){
                for(OpcionesProductos tmp1:opciones){
                    if(!tmp1.equals(opcionesProductos))
                        retorno.add(tmp1);
                }
            }
        }
        return retorno;
    }

    public static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    public static Set<Color> colores(List<Combinacion> combinacions){
        Set<Color> colores=new HashSet<>();
        for(Combinacion c:combinacions){
            for(OpcionesProductos op:c.getOpcionesProductosList()){
                if(op instanceof Color){
                    colores.add(((Color) op));
                }
            }
        }
        return colores;
    }

    public static Set<Combinacion> tallasPorColor(List<Combinacion> combinacions, int color){
        Set<Combinacion> combinacionSet=new HashSet<>();
        for(Combinacion c:combinacions){
            for(OpcionesProductos op:c.getOpcionesProductosList()){
                if(op instanceof Color && ((Color) op).getColor()==color){
                    combinacionSet.add(c);
                }
            }
        }
        return combinacionSet;
    }

    public static Map<Integer, Producto> todosProductos(Map<Integer, Categoria> todasLasCategorias){
        Map<Integer, Producto> retorno=new HashMap<>();
        for(Categoria c:todasLasCategorias.values()){
            for(Producto p:c.getProductos()){
                retorno.put(p.getId(), p);
            }
        }
        return retorno;
    }


    public static String creaCestaXML(Cesta cesta, Direccion entrega, Direccion factura, Integer idLenguaje, Integer idMoneda) throws ParserConfigurationException, TransformerException, TransformerConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document=implementation.createDocument(null ,"prestashop",null);

        document.setXmlVersion("1.0");
        Attr atributoXlink=document.createAttribute("xmlns:xlink");
        atributoXlink.setValue("http://www.w3.org/1999/xlink");
        document.getDocumentElement().setAttributeNode(atributoXlink);


        //Obtenemos el elemento raiz
        Element raiz=document.getDocumentElement();
        //Empezamos a construir el elemento
        Element cart = document.createElement("cart");
        Element id=document.createElement("id");
        cart.appendChild(id);
        Element id_address_delivery=document.createElement("id_address_delivery");
        cart.appendChild(id_address_delivery);
        Element id_address_invoice=document.createElement("id_address_invoice");
        cart.appendChild(id_address_invoice);
        Element id_currency=document.createElement("id_currency");
        id_currency.appendChild(document.createTextNode(idMoneda.toString()));
        cart.appendChild(id_currency);
        Element id_customer=document.createElement("id_customer");
        cart.appendChild(id_customer);
        Element id_guest=document.createElement("id_guest");
        cart.appendChild(id_guest);
        Element id_lang=document.createElement("id_lang");
        id_lang.appendChild(document.createTextNode(idLenguaje.toString()));
        cart.appendChild(id_lang);
        Element id_shop_group=document.createElement("id_shop_group");
        cart.appendChild(id_shop_group);
        Element id_shop=document.createElement("id_shop");
        cart.appendChild(id_shop);
        Element id_carrier=document.createElement("id_carrier");
        cart.appendChild(id_carrier);
        Element recyclable=document.createElement("recyclable");
        cart.appendChild(recyclable);
        Element gift=document.createElement("gift");
        cart.appendChild(gift);
        Element gift_message=document.createElement("gift_message");
        cart.appendChild(gift_message);
        Element mobile_theme=document.createElement("mobile_theme");
        cart.appendChild(mobile_theme);
        Element delivery_option=document.createElement("delivery_option");
        cart.appendChild(delivery_option);
        Element secure_key=document.createElement("secure_key");
        cart.appendChild(secure_key);
        Element allow_seperated_package=document.createElement("allow_seperated_package");
        cart.appendChild(allow_seperated_package);
        Element date_add=document.createElement("date_add");
        cart.appendChild(date_add);
        Element date_upd=document.createElement("date_upd");
        cart.appendChild(date_upd);
        Element associations=document.createElement("associations");
        Element cart_rows=document.createElement("cart_rows");
        //Cada elemento de la cesta
        for(Pedido p:cesta.getCesta()){
            Element cart_row=document.createElement("cart_row");

            Element id_product=document.createElement("id_product");
            id_product.appendChild(document.createTextNode(p.getCombinacion().getIdProducto()+""));
            cart_row.appendChild(id_product);

            Element id_product_attribute=document.createElement("id_product_attribute");
            id_product_attribute.appendChild(document.createTextNode(p.getCombinacion().getId()+""));
            cart_row.appendChild(id_product_attribute);

            Element id_address_deliveryRow=document.createElement("id_address_delivery");
            id_address_deliveryRow.appendChild(document.createTextNode(entrega.getIdDireccion()+""));
            cart_row.appendChild(id_address_deliveryRow);

            Element quantity=document.createElement("quantity");
            quantity.appendChild(document.createTextNode(p.getCantidad()+""));
            cart_row.appendChild(quantity);

            cart_rows.appendChild(cart_row);
        }
        associations.appendChild(cart_rows);
        cart.appendChild(associations);
        raiz.appendChild(cart);

        Transformer transformer = null;
        transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(new StringWriter());
        DOMSource source = new DOMSource(document);
        transformer.transform(source, result);
        String xmlString = result.getWriter().toString();
        return xmlString;

    }

    public static String creaPedidoXML(int idCesta,Cesta cesta, Direccion entrega, Direccion factura, Integer idMoneda, Integer idLenguaje, Integer idCliente, Integer idTransportista,
                                       String modulo, String pago, float ratioDeConversion) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document=implementation.createDocument(null ,"prestashop",null);

        document.setXmlVersion("1.0");
        Attr atributoXlink=document.createAttribute("xmlns:xlink");
        atributoXlink.setValue("http://www.w3.org/1999/xlink");
        document.getDocumentElement().setAttributeNode(atributoXlink);

        //Obtenemos el elemento raiz
        Element raiz=document.getDocumentElement();
        //Empezamos a construir el elemento
        Element order = document.createElement("order");

        Element id_address_delivery=document.createElement("id_address_delivery");
        id_address_delivery.appendChild(document.createTextNode(entrega.getIdDireccion()+""));
        order.appendChild(id_address_delivery);

        Element id_address_invoice=document.createElement("id_address_invoice");
        id_address_invoice.appendChild(document.createTextNode(factura.getIdDireccion()+""));
        order.appendChild(id_address_invoice);

        Element id_cart=document.createElement("id_cart");
        id_cart.appendChild(document.createTextNode(idCesta+""));
        order.appendChild(id_cart);

        Element id_currency=document.createElement("id_currency");
        id_currency.appendChild(document.createTextNode(idMoneda.toString()));
        order.appendChild(id_currency);

        Element id_lang=document.createElement("id_lang");
        id_lang.appendChild(document.createTextNode(idLenguaje.toString()));
        order.appendChild(id_lang);

        Element id_customer=document.createElement("id_customer");
        id_customer.appendChild(document.createTextNode(idCliente.toString()));
        order.appendChild(id_customer);

        Element id_carrier=document.createElement("id_carrier");
        id_carrier.appendChild(document.createTextNode(idTransportista.toString()));
        order.appendChild(id_carrier);

        Element current_state=document.createElement("current_state");
        order.appendChild(current_state);

        Element module=document.createElement("module");
        module.appendChild(document.createTextNode(modulo));
        order.appendChild(module);

        Element invoice_number=document.createElement("invoice_number");
        order.appendChild(invoice_number);

        Element invoice_date=document.createElement("invoice_date");
        order.appendChild(invoice_date);

        Element delivery_number=document.createElement("delivery_number");
        order.appendChild(delivery_number);

        Element delivery_date=document.createElement("delivery_date");
        order.appendChild(delivery_date);

        Element valid=document.createElement("valid");
        order.appendChild(valid);

        Element date_add=document.createElement("date_add");
        order.appendChild(date_add);

        Element date_upd=document.createElement("date_upd");
        order.appendChild(date_upd);

        Element shipping_number=document.createElement("shipping_number");
        order.appendChild(shipping_number);

        Element id_shop_group=document.createElement("id_shop_group");
        order.appendChild(id_shop_group);

        Element id_shop=document.createElement("id_shop");
        order.appendChild(id_shop);

        Element secure_key=document.createElement("secure_key");
        order.appendChild(secure_key);

        Element payment=document.createElement("payment");
        payment.appendChild(document.createTextNode(pago));
        order.appendChild(payment);

        Element recyclable=document.createElement("recyclable");
        order.appendChild(recyclable);

        Element gift=document.createElement("gift");
        order.appendChild(gift);

        Element gift_message=document.createElement("gift_message");
        order.appendChild(gift_message);

        Element mobile_theme=document.createElement("mobile_theme");
        order.appendChild(mobile_theme);

        Element total_discounts=document.createElement("total_discounts");
        order.appendChild(total_discounts);

        Element total_discounts_tax_incl=document.createElement("total_discounts_tax_incl");
        order.appendChild(total_discounts_tax_incl);

        Element total_discounts_tax_excl=document.createElement("total_discounts_tax_excl");
        order.appendChild(total_discounts_tax_excl);

        Element total_paid=document.createElement("total_paid");
        total_paid.appendChild(document.createTextNode(cesta.calcularPrecioTotal()+""));
        order.appendChild(total_paid);

        Element total_paid_tax_incl=document.createElement("total_paid_tax_incl");
        order.appendChild(total_paid_tax_incl);

        Element total_paid_tax_excl=document.createElement("total_paid_tax_excl");
        order.appendChild(total_paid_tax_excl);

        Element total_paid_real=document.createElement("total_paid_real");
        total_paid_real.appendChild(document.createTextNode(cesta.calcularPrecioTotal()+""));
        order.appendChild(total_paid_real);

        Element total_products=document.createElement("total_products");
        total_products.appendChild(document.createTextNode(cesta.calcularPrecioTotal()+""));
        order.appendChild(total_products);

        Element total_products_wt=document.createElement("total_products_wt");
        total_products_wt.appendChild(document.createTextNode(cesta.calcularPrecioTotal()+""));
        order.appendChild(total_products_wt);

        Element total_shipping=document.createElement("total_shipping");
        order.appendChild(total_shipping);

        Element total_shipping_tax_incl=document.createElement("total_shipping_tax_incl");
        order.appendChild(total_shipping_tax_incl);

        Element total_shipping_tax_excl=document.createElement("total_shipping_tax_excl");
        order.appendChild(total_shipping_tax_excl);

        Element carrier_tax_rate=document.createElement("carrier_tax_rate");
        order.appendChild(carrier_tax_rate);

        Element total_wrapping=document.createElement("total_wrapping");
        order.appendChild(total_wrapping);

        Element total_wrapping_tax_incl=document.createElement("total_wrapping_tax_incl");
        order.appendChild(total_wrapping_tax_incl);

        Element total_wrapping_tax_excl=document.createElement("total_wrapping_tax_excl");
        order.appendChild(total_wrapping_tax_excl);

        Element round_mode=document.createElement("round_mode");
        order.appendChild(round_mode);

        Element conversion_rate=document.createElement("conversion_rate");
        conversion_rate.appendChild(document.createTextNode(ratioDeConversion+""));
        order.appendChild(conversion_rate);

        Element reference=document.createElement("reference");
        order.appendChild(reference);

        Element associations=document.createElement("associations");
        Element order_rows=document.createElement("order_rows");
        //Cada elemento de la cesta
        for(Pedido p:cesta.getCesta()){
            Element order_row=document.createElement("order_row");

            Element id=document.createElement("id");
            order_row.appendChild(id);

            Element product_id=document.createElement("product_id");
            product_id.appendChild(document.createTextNode(p.getCombinacion().getIdProducto()+""));
            order_row.appendChild(product_id);

            Element product_attribute_id=document.createElement("product_attribute_id");
            product_attribute_id.appendChild(document.createTextNode(p.getCombinacion().getId()+""));
            order_row.appendChild(product_attribute_id);

            Element product_quantity=document.createElement("product_quantity");
            product_quantity.appendChild(document.createTextNode(p.getCantidad()+""));
            order_row.appendChild(product_quantity);

            Element product_name=document.createElement("product_name");
            order_row.appendChild(product_name);

            Element product_reference=document.createElement("product_reference");
            order_row.appendChild(product_reference);

            Element product_ean13=document.createElement("product_ean13");
            order_row.appendChild(product_ean13);

            Element product_upc=document.createElement("product_upc");
            order_row.appendChild(product_upc);

            Element product_price=document.createElement("product_price");
            order_row.appendChild(product_price);

            Element unit_price_tax_incl=document.createElement("unit_price_tax_incl");
            order_row.appendChild(unit_price_tax_incl);

            Element unit_price_tax_excl=document.createElement("unit_price_tax_excl");
            order_row.appendChild(unit_price_tax_excl);


            order_rows.appendChild(order_row);
        }
        associations.appendChild(order_rows);
        order.appendChild(associations);
        raiz.appendChild(order);

        Transformer transformer = null;
        transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(new StringWriter());
        DOMSource source = new DOMSource(document);
        transformer.transform(source, result);
        String xmlString = result.getWriter().toString();
        return xmlString;

    }



}
