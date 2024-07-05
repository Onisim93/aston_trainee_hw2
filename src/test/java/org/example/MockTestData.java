package org.example;

import org.example.dto.AuthorDto;
import org.example.dto.BookDto;
import org.example.dto.GenreDto;
import org.example.model.AuthorEntity;
import org.example.model.BookEntity;
import org.example.model.GenreEntity;

import java.util.List;

public class MockTestData {

    public static final GenreEntity genre1 = GenreEntity.builder()
            .id(1)
            .name("Поэма")
            .description("Поэма - литературный жанр, в котором соединяются черты лирического и эпического родов. Так, от эпоса поэма наследует повествование о событиях, проблематику, образы персонажей, а от лирики – яркую эмоциональность изображения, сосредоточенность на чувствах и переживаниях главного героя.")
            .build();

    public static final GenreEntity genre2 = GenreEntity.builder()
            .id(2)
            .name("Драма")
            .description("Драма - жанр литературы, повествующий о серьезных, иногда печальных событиях, но не заканчивающийся трагическим финалом. Драму по определению относят к серьезным жанрам литературы. В отличие от легкой литературы драма сосредоточена не на сюжете, а на переживаниях героев в обстоятельствах, предложенных сюжетом.")
            .build();

    public static final GenreEntity genre3 = GenreEntity.builder()
            .id(3)
            .name("Роман")
            .description("Рома́н — литературный жанр, чаще прозаический, зародившийся в Средние века у романских народов, как рассказ на народном языке и ныне превратившийся в самый распространённый вид эпической литературы, изображающий жизнь персонажа с её волнующими страстями, борьбой, социальными противоречиями и стремлениями к идеалу.")
            .build();

    public static final GenreEntity genre4 = GenreEntity.builder()
            .id(4)
            .name("Роман в стихах")
            .description("РОМА́Н В СТИХА́Х — лиро-эпический жанр, предполагающий соединение черт как эпоса, так и лирики в пределах одного произведения. Характерные черты романа в стихах: наличие сюжетной линии, системы персонажей, конфликта; способ отображения действительности (эпос).")
            .build();

    public static final GenreEntity genre5 = GenreEntity.builder()
            .id(5)
            .name("Исторический роман")
            .description("Исторический роман — произведение художественной литературы, события которого разворачиваются на фоне исторических событий и с участием реальных исторических личностей. В историческом романе автор с помощью художественных средств описывает реальные исторические события, показывая их глазами действующих лиц.")
            .build();

    public static final GenreEntity genre6 = GenreEntity.builder()
            .id(6)
            .name("Русская сказка")
            .description("Русская народная сказка (до XVII века баснь, байка) — эпическое художественное произведение русского народа, преимущественно прозаического, волшебного и авантюрного или бытового характера с установкой на вымысел (определение Э. В. Померанцевой); один из основных жанров фольклорной прозы.")
            .build();

    public static final GenreEntity genre7 = GenreEntity.builder()
            .id(7)
            .name("Сборник стихотворений")
            .description("Сборник небольших поэтических произведений, написанных по законам стихосложения. Стихотворение – это жанр такого рода литературы, как лирика.")
            .build();

    public static final GenreEntity genre8 = GenreEntity.builder()
            .id(8)
            .name("Историческая повесть")
            .description("Историческая повесть - жанр повествовательной литературы. В ней изображаются в художественной форме события и лица какого-нибудь исторического периода. Основные требования к исторической повести такие: в основе - исторические источники - архивы, летописи, прошлое -даже далёкое должно быть понятно читателю.")
            .build();

    public static final GenreEntity genre9 = GenreEntity.builder()
            .id(9)
            .name("Психологический Роман")
            .description("Психологический роман — роман, центральное внимание в котором уделяется характерам персонажей, их душевным переживаниям и развитию. Сюжет в психологическом романе играет вспомогательную роль для раскрытия характеров героев, их трансформации и движений души.")
            .build();

    public static final GenreEntity genre10 = GenreEntity.builder()
            .id(10)
            .name("Фэнтези")
            .description("«фантазия»; по-русски слово «фэнтези» употребляется то в женском, то в среднем роде[2]) — жанр современного искусства, разновидность фантастики. Произведения жанра фэнтези основываются на мифологических и сказочных мотивах, переосмысленных или переработанных авторами.")
            .build();

    public static final Integer newGenreId = 11;
    public static final GenreEntity newGenre = GenreEntity.builder()
            .name("Фантастика")
            .description("Фантастика – это любая история, в которой автор нарушил законы реального мира. Например, предположил существование невероятных технологий, удивительных планет или незнакомых форм жизни. Фантастика появилась во времена больших открытий.")
            .build();

    public static final GenreDto newGenreDto = new GenreDto("Фантастика",  "Фантастика – это любая история, в которой автор нарушил законы реального мира. Например, предположил существование невероятных технологий, удивительных планет или незнакомых форм жизни. Фантастика появилась во времена больших открытий.");


    public static final GenreEntity newGenreCreated = GenreEntity.builder()
            .id(newGenreId)
            .name("Фантастика")
            .description("Фантастика – это любая история, в которой автор нарушил законы реального мира. Например, предположил существование невероятных технологий, удивительных планет или незнакомых форм жизни. Фантастика появилась во времена больших открытий.")
            .build();

    public static final GenreDto newGenreCreatedDto = new GenreDto(newGenreId,"Фантастика",  "Фантастика – это любая история, в которой автор нарушил законы реального мира. Например, предположил существование невероятных технологий, удивительных планет или незнакомых форм жизни. Фантастика появилась во времена больших открытий.");

    public static final GenreEntity updatedGenre = GenreEntity.builder()
            .id(9)
            .name("updated Психологический Роман")
            .description("updated Психологический роман — роман, центральное внимание в котором уделяется характерам персонажей, их душевным переживаниям и развитию. Сюжет в психологическом романе играет вспомогательную роль для раскрытия характеров героев, их трансформации и движений души.")
            .build();

    public static final GenreEntity notValidUpdatedGenreData = GenreEntity.builder()
            .id(9)
            .build();

    public static final GenreEntity notValidUpdatedGenreId = GenreEntity.builder()
            .id(99)
            .name("updated Психологический Роман")
            .description("updated Психологический роман — роман, центральное внимание в котором уделяется характерам персонажей, их душевным переживаниям и развитию. Сюжет в психологическом романе играет вспомогательную роль для раскрытия характеров героев, их трансформации и движений души.")
            .build();

    public static final GenreDto updatedGenreDto = new GenreDto(9, "updated Психологический Роман", "updated Психологический роман — роман, центральное внимание в котором уделяется характерам персонажей, их душевным переживаниям и развитию. Сюжет в психологическом романе играет вспомогательную роль для раскрытия характеров героев, их трансформации и движений души.");

    public static final List<GenreEntity> allGenres = List.of(genre1, genre2, genre3, genre4, genre5, genre6, genre7, genre8, genre9, genre10);

    public static final int newAuthorId = 7;

    public static final AuthorEntity newAuthor = AuthorEntity.builder()
            .name("Горький Алексей Максимович")
            .bio("Годы жизни 1868-1936. Горький Алексей Максимович -  русский советский писатель, поэт, прозаик, драматург, журналист и общественный деятель, публицист.")
            .build();

    public static final AuthorEntity notValidNewAuthor = AuthorEntity.builder().build();

    public static final AuthorEntity newAuthorCreated = AuthorEntity.builder()
            .id(newAuthorId)
            .name("Горький Алексей Максимович")
            .bio("Годы жизни 1868-1936. Горький Алексей Максимович -  русский советский писатель, поэт, прозаик, драматург, журналист и общественный деятель, публицист.")
            .build();
    public static final AuthorDto newAuthorDto = new AuthorDto("Горький Алексей Максимович", "Годы жизни 1868-1936. Горький Алексей Максимович -  русский советский писатель, поэт, прозаик, драматург, журналист и общественный деятель, публицист.");

    public static final AuthorDto newAuthorCreatedDto = new AuthorDto(newAuthorId, "Горький Алексей Максимович", "Годы жизни 1868-1936. Горький Алексей Максимович -  русский советский писатель, поэт, прозаик, драматург, журналист и общественный деятель, публицист.");

    public static final AuthorEntity author1 = AuthorEntity.builder()
            .id(1)
            .name("Пушкин Александр Сергеевич")
            .bio("Годы жизни 1799-1837. Александр Сергеевич Пушкин - великий русский поэт, драматург и прозаик, считающийся основателем современной русской литературы. Родился в Москве в дворянской семье. Воспитывался в духе европейского Просвещения. Известен своими произведениями, такими как “Евгений Онегин”, “Руслан и Людмила”, “Капитанская дочка”. Пушкин был участником декабристского движения и вел активную литературную и общественную деятельность до своей трагической гибели на дуэли в 1837 году.")
            .build();

    public static final AuthorEntity updatedAuthor = AuthorEntity.builder()
            .id(1)
            .name("Пушкин Александр Сергеевич update")
            .bio("update Годы жизни 1799-1837. Александр Сергеевич Пушкин - великий русский поэт, драматург и прозаик, считающийся основателем современной русской литературы. Родился в Москве в дворянской семье. Воспитывался в духе европейского Просвещения. Известен своими произведениями, такими как “Евгений Онегин”, “Руслан и Людмила”, “Капитанская дочка”. Пушкин был участником декабристского движения и вел активную литературную и общественную деятельность до своей трагической гибели на дуэли в 1837 году.")
            .build();

    public static final AuthorEntity notValidUpdatedAuthorId = AuthorEntity.builder()
            .id(100)
            .name("Пушкин Александр Сергеевич update")
            .bio("update Годы жизни 1799-1837. Александр Сергеевич Пушкин - великий русский поэт, драматург и прозаик, считающийся основателем современной русской литературы. Родился в Москве в дворянской семье. Воспитывался в духе европейского Просвещения. Известен своими произведениями, такими как “Евгений Онегин”, “Руслан и Людмила”, “Капитанская дочка”. Пушкин был участником декабристского движения и вел активную литературную и общественную деятельность до своей трагической гибели на дуэли в 1837 году.")
            .build();

    public static final AuthorEntity notValidUpdatedAuthorData = AuthorEntity.builder().id(1).build();


    public static final AuthorDto updatedAuthorDto = new AuthorDto(1, "Пушкин Александр Сергеевич update", "update Годы жизни 1799-1837. Александр Сергеевич Пушкин - великий русский поэт, драматург и прозаик, считающийся основателем современной русской литературы. Родился в Москве в дворянской семье. Воспитывался в духе европейского Просвещения. Известен своими произведениями, такими как “Евгений Онегин”, “Руслан и Людмила”, “Капитанская дочка”. Пушкин был участником декабристского движения и вел активную литературную и общественную деятельность до своей трагической гибели на дуэли в 1837 году.");

    public static final AuthorEntity author2 = AuthorEntity.builder()
            .id(2)
            .name("Толстой Лев Николаевич")
            .bio("Годы жизни 1828-1910. Лев Николаевич Толстой - один из самых известных русских писателей и философов, автор романов “Война и мир”, “Анна Каренина” и “Воскресение”. Родился в Тульской губернии в аристократической семье. Получил образование в Казанском университете. Толстой также известен своими религиозными и философскими трудами. В последние годы жизни отказался от своего богатства и стал вести скромный образ жизни, что вызвало широкий общественный резонанс.")
            .build();

    public static final AuthorEntity author3 = AuthorEntity.builder()
            .id(3)
            .name("Достоевский Федор Михайлович")
            .bio("Годы жизни 1821-1881. Федор Михайлович Достоевский - выдающийся русский писатель, философ и публицист, автор таких произведений, как “Преступление и наказание”, “Идиот”, “Братья Карамазовы” и “Бесы”. Родился в Москве в семье врача. В молодости был арестован за участие в революционном кружке и сослан в Сибирь. Творчество Достоевского глубоко исследует человеческую природу и нравственные дилеммы.")
            .build();

    public static final AuthorEntity author4 = AuthorEntity.builder()
            .id(4)
            .name("Чехов Антон Павлович")
            .bio("Годы жизни 1860-1904. Антон Павлович Чехов - знаменитый русский драматург и прозаик, признанный мастер короткого рассказа. Родился в Таганроге в семье торговца. Получил медицинское образование в Московском университете. Среди его наиболее известных пьес “Вишневый сад”, “Три сестры”, “Чайка”. Чехов также написал множество коротких рассказов, исследующих человеческие отношения и социальные проблемы.")
            .build();

    public static final AuthorEntity author5 = AuthorEntity.builder()
            .id(5)
            .name("Пастернак Борис Леонидович")
            .bio("Годы жизни 1890-1960. Борис Леонидович Пастернак - русский поэт, писатель и переводчик, лауреат Нобелевской премии по литературе 1958 года. Родился в Москве в творческой семье художника и пианистки. Учился в Московском университете. Наибольшую известность получил его роман “Доктор Живаго”, за который он был удостоен Нобелевской премии, но был вынужден отказаться от нее под давлением советских властей. Пастернак также известен своими поэтическими сборниками и переводами классических произведений мировой литературы на русский язык.")
            .build();

    public static final AuthorEntity author6 = AuthorEntity.builder()
            .id(6)
            .name("Булгаков Михаил Афанасьевич")
            .bio("Годы жизни 1891-1940. Булгаков Михаил Афанасьевич - русский писатель советского периода, врач, драматург, театральный режиссёр и актёр. Автор романов, повестей, рассказов, пьес, киносценариев и фельетонов, написанных в 1920-е годы.")
            .build();

    public static final List<AuthorEntity> allAuthors = List.of(author1, author2, author3, author4, author5, author6);

    public static final BookEntity book1 = BookEntity.builder()
            .id(1)
            .title("Евгений Онегин")
            .description("Основное произведение Пушкина, описывающее жизнь светского молодого человека Евгения Онегина и его взаимоотношения с Татьяной Лариной.")
            .publishedDate("1825-1832")
            .isbn("978-0140448030")
            .author(author1)
            .genres(List.of(genre4))
            .build();

    public static final BookEntity book2 = BookEntity.builder()
            .id(2)
            .title("Руслан и Людмила")
            .description("Эпическая поэма, основанная на народных русских сказках, повествует о похищении невесты Руслана - Людмилы, дочери князя Владимира.")
            .publishedDate("1820")
            .isbn("978-0486293356")
            .author(author1)
            .genres(List.of(genre1, genre6))
            .build();

    public static final BookEntity book3 = BookEntity.builder()
            .id(3)
            .title("Капитанская дочка")
            .description("Историческая повесть, описывающая события Крестьянской войны под предводительством Пугачева и историю любви Петра Гринева и Маши Мироновой.")
            .publishedDate("1836")
            .isbn("978-0140446753")
            .author(author1)
            .genres(List.of(genre8))
            .build();

    public static final BookEntity book4 = BookEntity.builder()
            .id(4)
            .title("Война и мир")
            .description("Эпическое произведение, описывающее жизнь русского общества во время Наполеоновских войн.")
            .publishedDate("1869")
            .isbn("978-1400079988")
            .author(author2)
            .genres(List.of(genre5))
            .build();

    public static final BookEntity book5 = BookEntity.builder()
            .id(5)
            .title("Анна Каренина")
            .description("Роман о трагической судьбе замужней женщины Анны Карениной, вступившей в любовные отношения с графом Вронским.")
            .publishedDate("1877")
            .isbn("978-0143035008")
            .author(author2)
            .genres(List.of(genre3))
            .build();

    public static final BookEntity book6 = BookEntity.builder()
            .id(6)
            .title("Воскресение")
            .description("Последний роман Толстого, рассказывающий о духовном пробуждении и искуплении князя Дмитрия Нехлюдова.")
            .publishedDate("1899")
            .isbn("978-0199536399")
            .author(author2)
            .genres(List.of(genre3))
            .build();

    public static final BookEntity book7 = BookEntity.builder()
            .id(7)
            .title("Преступление и наказание")
            .description("История о студенте Родионе Раскольникове, который совершает убийство и пытается оправдать его высшими целями.")
            .publishedDate("1866")
            .isbn("978-0140449136")
            .author(author3)
            .genres(List.of(genre9))
            .build();

    public static final BookEntity book8 = BookEntity.builder()
            .id(8)
            .title("Идиот")
            .description("Роман о князе Мышкине, который возвращается в Россию после лечения в Швейцарии и пытается жить по принципам добра и любви, сталкиваясь с жестокостью и непониманием.")
            .publishedDate("1869")
            .isbn("978-0140447927")
            .author(author3)
            .genres(List.of(genre3))
            .build();

    public static final BookEntity book9 = BookEntity.builder()
            .id(9)
            .title("Братья Карамазовы")
            .description("Роман, исследующий глубокие философские и нравственные вопросы через историю семьи Карамазовых.")
            .publishedDate("1880")
            .isbn("978-0374528379")
            .author(author3)
            .genres(List.of(genre3))
            .build();

    public static final BookEntity book10 = BookEntity.builder()
            .id(10)
            .title("Вишневый сад")
            .description("Пьеса о судьбе семьи Раневских, которая теряет свое имение и вишневый сад из-за финансовых проблем.")
            .publishedDate("1904")
            .isbn("978-0486266824")
            .author(author4)
            .genres(List.of(genre2))
            .build();

    public static final BookEntity book11 = BookEntity.builder()
            .id(11)
            .title("Три сестры")
            .description("Пьеса о трех сестрах Прозоровых, мечтающих вернуться в Москву и изменить свою жизнь.")
            .publishedDate("1901")
            .isbn("978-0486272801")
            .author(author4)
            .genres(List.of(genre2))
            .build();

    public static final BookEntity book12 = BookEntity.builder()
            .id(12)
            .title("Чайка")
            .description("Пьеса о сложных взаимоотношениях между членами творческой семьи и их знакомыми.")
            .publishedDate("1896")
            .isbn("978-0486408835")
            .author(author4)
            .genres(List.of(genre2))
            .build();

    public static final BookEntity book13 = BookEntity.builder()
            .id(13)
            .title("Доктор Живаго")
            .description("Роман о судьбе врача Юрия Живаго на фоне революции и Гражданской войны в России.")
            .publishedDate("1957")
            .isbn("978-0099448426")
            .author(author5)
            .genres(List.of(genre3))
            .build();

    public static final BookEntity book14 = BookEntity.builder()
            .id(14)
            .title("Моя сестра - жизнь")
            .description("Сборник поэзии, написанный в период революции, посвященный любви и природе.")
            .publishedDate("1922")
            .isbn("978-0810113551")
            .author(author5)
            .genres(List.of(genre7))
            .build();

    public static final BookEntity book15 = BookEntity.builder()
            .id(15)
            .title("Земля")
            .description("Поэтический сборник, включающий стихи, написанные в разные периоды жизни Пастернака и посвященные разным темам: любви, природе, философии.")
            .publishedDate("1956")
            .isbn("978-0810113550")
            .author(author5)
            .genres(List.of(genre7))
            .build();

    public static final BookEntity notValidBook = BookEntity.builder().build();
    public static final GenreEntity notValidGenre = GenreEntity.builder().build();
    public static final AuthorEntity notValidAuthor = AuthorEntity.builder().build();



    public static final Integer idForNewBook = 16;
    public static final BookEntity newBook = BookEntity.builder()
            .title("Земля NEW")
            .description("NEW Поэтический сборник, включающий стихи, написанные в разные периоды жизни Пастернака и посвященные разным темам: любви, природе, философии.")
            .publishedDate("1956")
            .isbn("978-1810113550")
            .author(author5)
            .genres(List.of(genre7, genre2))
            .build();

    public static final BookDto newBookDto = new BookDto("Земля NEW", "NEW Поэтический сборник, включающий стихи, написанные в разные периоды жизни Пастернака и посвященные разным темам: любви, природе, философии.", "1956", "978-1810113550", author5.getId(), List.of(genre7.getId(), genre2.getId()));


    public static final BookEntity newBookCreated = BookEntity.builder()
            .id(idForNewBook)
            .title("Земля NEW")
            .description("NEW Поэтический сборник, включающий стихи, написанные в разные периоды жизни Пастернака и посвященные разным темам: любви, природе, философии.")
            .publishedDate("1956")
            .isbn("978-1810113550")
            .author(author5)
            .genres(List.of(genre7, genre2))
            .build();

    public static final BookDto newBookCreatedDto = new BookDto(idForNewBook, "Земля NEW", "NEW Поэтический сборник, включающий стихи, написанные в разные периоды жизни Пастернака и посвященные разным темам: любви, природе, философии.", "1956", "978-1810113550", author5.getId(), List.of(genre7.getId(), genre2.getId()));

    public static final BookEntity updatedBook = BookEntity.builder()
            .id(15)
            .title("updated Земля")
            .description("updated Поэтический сборник, включающий стихи, написанные в разные периоды жизни Пастернака и посвященные разным темам: любви, природе, философии.")
            .publishedDate("1958")
            .isbn("978-0817713550")
            .author(author5)
            .genres(List.of(genre7, genre3))
            .build();

    public static final BookEntity notValidUpdatedBookId = BookEntity.builder()
            .id(150)
            .title("updated Земля")
            .description("updated Поэтический сборник, включающий стихи, написанные в разные периоды жизни Пастернака и посвященные разным темам: любви, природе, философии.")
            .publishedDate("1958")
            .isbn("978-0817713550")
            .author(author5)
            .genres(List.of(genre7, genre3))
            .build();

    public static final BookEntity notValidUpdatedBookData = BookEntity.builder().id(15).build();


    public static final BookDto updatedBookDto = new BookDto(15, "updated Земля", "updated Поэтический сборник, включающий стихи, написанные в разные периоды жизни Пастернака и посвященные разным темам: любви, природе, философии.", "1958", "978-0817713550", author5.getId(), List.of(genre7.getId(), genre3.getId()));

    public static final BookEntity newAuthorBook = BookEntity.builder()
            .title("Капитанская дочка")
            .description("Исторический роман (или повесть) Александра Пушкина, действие которого происходит во время восстания Емельяна Пугачёва. Впервые опубликован без указания имени автора в 4-й книжке журнала «Современник», поступившей в продажу в последней декаде 1836 года.")
            .isbn("9788475251288")
            .publishedDate("1836")
            .genres(List.of(genre5))
            .build();

    public static final BookEntity newAuthorBookCreated = BookEntity.builder()
            .id(16)
            .title("Капитанская дочка")
            .description("Исторический роман (или повесть) Александра Пушкина, действие которого происходит во время восстания Емельяна Пугачёва. Впервые опубликован без указания имени автора в 4-й книжке журнала «Современник», поступившей в продажу в последней декаде 1836 года.")
            .isbn("9788475251288")
            .publishedDate("1836")
            .author(author1)
            .genres(List.of(genre5))
            .build();

    public static final BookDto newAuthorBookDto = new BookDto("Капитанская дочка", "Исторический роман (или повесть) Александра Пушкина, действие которого происходит во время восстания Емельяна Пугачёва. Впервые опубликован без указания имени автора в 4-й книжке журнала «Современник», поступившей в продажу в последней декаде 1836 года.", "1836", "9788475251288", List.of(5));
    public static final BookDto newAuthorBookCreatedDto = new BookDto(16,"Капитанская дочка", "Исторический роман (или повесть) Александра Пушкина, действие которого происходит во время восстания Емельяна Пугачёва. Впервые опубликован без указания имени автора в 4-й книжке журнала «Современник», поступившей в продажу в последней декаде 1836 года.", "1836", "9788475251288", 1, List.of(5));

    public static final List<BookEntity> allBooks = List.of(book1, book2, book3, book4, book5, book6, book7, book8, book9, book10, book11, book12, book13, book14, book15);
    public static final List<BookEntity> booksForAuthor1 = List.of(book1, book2, book3);
    public static final List<BookEntity> booksForGenre3 = List.of(book5, book6, book8, book9, book13);
    public static final List<BookEntity> booksForGenre3AndAuthor2 = List.of(book5, book6);
}
