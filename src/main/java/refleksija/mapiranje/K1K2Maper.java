package refleksija.mapiranje;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author д06ри, dobri7@gmail.com
 */
@Mapper
public interface K1K2Maper {

    K1K2Maper INSTANCE = Mappers.getMapper(K1K2Maper.class);

    @Mappings({
        @Mapping(source = "id", target = "idK"),
        @Mapping(source = "naziv", target = "naziv"),
        @Mapping(source = "kol", target = "kolicina"),
        @Mapping(source = "vred", target = "vrednost"),
        @Mapping(source = "list", target = "listaSvojstava"),
        @Mapping(source = "taskWithResultList", target = "LK1")
    })

    /*
    @Mapping(source = "idK", target = "id")
    @Mapping(source = "kol", target = "kolicina")
    */

    Klasa2 mapiraj(Klasa1 source);
}
