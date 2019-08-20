package refleksija.mapiranje;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import multithreading.callable.TaskWithResult;
import refleksija.mapiranje.Klasa2.Klasa2Builder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-08-20T14:57:52+0200",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
public class K1K2MaperImpl implements K1K2Maper {

    @Override
    public Klasa2 mapiraj(Klasa1 source) {
        if ( source == null ) {
            return null;
        }

        Klasa2Builder klasa2 = Klasa2.builder();

        klasa2.idK( source.getId() );
        List<String> list = source.getList();
        if ( list != null ) {
            klasa2.listaSvojstava( new ArrayList<String>( list ) );
        }
        klasa2.vrednost( source.getVred() );
        List<TaskWithResult> list1 = source.getTaskWithResultList();
        if ( list1 != null ) {
            klasa2.LK1( new ArrayList<TaskWithResult>( list1 ) );
        }
        klasa2.naziv( source.getNaziv() );
        klasa2.kolicina( source.getKol() );

        return klasa2.build();
    }
}
