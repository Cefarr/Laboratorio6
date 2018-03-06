/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.services.client;



import edu.eci.pdsw.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.pdsw.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.entities.Item;
import edu.eci.pdsw.samples.entities.ItemRentado;
import edu.eci.pdsw.samples.entities.TipoItem;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author hcadavid
 */
public class MyBatisExample {

    /**
     * Método que construye una fábrica de sesiones de MyBatis a partir del
     * archivo de configuración ubicado en src/main/resources
     *
     * @return instancia de SQLSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }

    /**
     * Programa principal de ejempo de uso de MyBATIS
     * @param args
     * @throws SQLException 
     */
    public static void main(String args[]) throws SQLException {
        SqlSessionFactory sessionfact = getSqlSessionFactory();

        SqlSession sqlss = sessionfact.openSession();

        
        //Crear el mapper y usarlo: 
        ClienteMapper cm=sqlss.getMapper(ClienteMapper.class);
        ItemMapper itm=sqlss.getMapper(ItemMapper.class);
        //cm...
        List<Cliente> re=cm.consultarClientes();
        /**
         * El cliente es: Felipe Rodriguezel id1026587456
            El cliente es: Javier Romeroel id1026677456
            El cliente es: Juan José Andradeel id1070017538
            El cliente es: Santiago Lopezel id1684264984
         */
        List<Item> lisIt=new ArrayList<Item>();
        int idCliente=1684264984;
        Cliente t= cm.consultarCliente(idCliente);
        Cliente cl1=new Cliente("Cesar Eduardo Lanos", 1111111, "111111", "Cra 1 #1-1", "cesar.lanos@algo.com");
        for(int i =0 ; i<re.size();i++){
            Cliente pre=re.get(i);
            System.out.println("El cliente es: "+pre.getNombre()+"el id"+pre.getDocumento());
        }
        System.out.println("Miremos al selecionado: "+t.getNombre()+" Identidad: "+t.getDocumento());

        TipoItem tip1=new TipoItem(99999212,"Pelicula sadica");
        Item it11=new Item (tip1, 1901902321, "Las historias sadicas", "Cuentan historias sadicas espeluznantes", Date.valueOf("2016-12-12"), 2000, "Dvd", "Terror");
        
        //registrarNuevoItemn(itm,it11);
        //lisIt=ConsultarItems(itm);
        for(int tt=0;tt<lisIt.size();tt++){
            Item op=lisIt.get(tt);
            System.out.println("Miremos el nombre del objeto"+op.getNombre()+"La descripcion"+op.getDescripcion()+"identificacion"+op.getId());
        
        }
        //registrarNuevaOrden(cm, cl1);// Funciona pero para que no bote error por llave duplicada 
        // LO comente   
        sqlss.commit();
        
        
        sqlss.close();

        
        
    }
    public static void registrarNuevaOrden(ClienteMapper pmap, Cliente p){
        
        TipoItem tip1=new TipoItem(100921921,"Pelicula medica");
        Item it1=new Item (tip1, 123454321, "Las historias medicas", "Cuentan historias meidcas espeluznantes", Date.valueOf("2016-12-12"), 2000, "Dvd", "Terror");
        
        ItemRentado itRen1= new ItemRentado(12, it1, Date.valueOf("2016-12-12"),Date.valueOf("2098-12-12"));
        pmap.agregarItemRentadoACliente(190, (int)p.getDocumento(), it1.getId(), Date.valueOf("2016-12-12"), Date.valueOf("2090-12-12"));
     
    }
    public static void registrarNuevoItemn(ItemMapper pmap,Item p){
         pmap.insertarItem(p);
    }
    
    public static List<Item> ConsultarItems(ItemMapper pmap){
        return pmap.consultarItems();
    }


}
