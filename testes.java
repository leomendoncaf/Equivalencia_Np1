public class HorarioAtendimentoTest {

    private HorarioAtendimento horarioAtendimento;
    private ServidorRemoto servidorRemoto;

    @Before
    public void setUp() {
        servidorRemoto = mock(ServidorRemoto.class);
        horarioAtendimento = new HorarioAtendimento(servidorRemoto);
    }

    @Test
    public void testCriarObjetoHorarioAtendimentoComJson() {
    String json = "{\"nomeDoProfessor\": \"João da Silva\", \"horarioDeAtendimento\": \"Segunda-feira das 14h às 16h\", \"periodo\": \"Integral\", \"sala\": \"105\", \"predio\": [\"1\", \"2\"]}";
    HorarioAtendimento horario = new HorarioAtendimento(json);
    
    assertEquals("João da Silva", horario.getNomeDoProfessor());
    assertEquals("Segunda-feira das 14h às 16h", horario.getHorarioDeAtendimento());
    assertEquals("Integral", horario.getPeriodo());
    assertEquals("105", horario.getSala());
    assertArrayEquals(new String[] {"1", "2"}, horario.getPredio());
}

@Test
public void testNomeProfessor() {
    String json = "{\"nomeDoProfessor\":\"João da Silva\",\"horarioDeAtendimento\":\"Segunda-feira das 14h às 16h\",\"periodo\":\"integral\",\"sala\":\"205\",\"predio\":[\"1\",\"2\",\"3\",\"4\",\"6\"]}";
    HorarioAtendimento horario = new HorarioAtendimento(json);
    assertEquals("João da Silva", horario.getNomeProfessor());
}

@Test
public void testHorarioAtendimento() {
    String json = "{\"nomeDoProfessor\":\"Fulano\",\"horarioDeAtendimento\":\"10h às 12h\",\"periodo\":\"integral\",\"sala\":\"Sala 101\",\"predio\":[\"1\",\"2\",\"3\",\"4\",\"6\"]}";
    HorarioAtendimento horario = new HorarioAtendimento(json);
    assertEquals("10h às 12h", horario.getHorarioAtendimento());
}

@Test
public void testPeriodoHorarioAtendimento() {
    String json = "{\"nomeDoProfessor\": \"Fulano\", \"horarioDeAtendimento\": \"10:00 - 12:00\", \"periodo\": \"integral\", \"sala\": \"1\", \"predio\":[\"1\",\"2\"]}";
    HorarioAtendimento horario = new HorarioAtendimento(json);
    assertEquals("integral", horario.getPeriodo());
}

@Test
public void testSalaDeAtendimento() {
    // JSON com informações de um professor fictício
    String json = "{\"nomeDoProfessor\": \"João Silva\"," +
                  "\"horarioDeAtendimento\": \"14h às 16h\"," +
                  "\"periodo\": \"Noturno\"," +
                  "\"sala\": \"B302\"," +
                  "\"predio\":[\"2\"]}";

    // Criação do objeto HorarioAtendimento a partir do JSON
    HorarioAtendimento ha = new HorarioAtendimento(json);

    // Verificação da sala de atendimento
    assertEquals("B302", ha.getSala());
}

@Test
public void testHorarioAtendimentoContemPredioCorreto() {
    // Cria o JSON com as informações de um professor
    String json = "{\"nomeDoProfessor\": \"João da Silva\", \"horarioDeAtendimento\": \"Segunda-feira, das 14h às 16h\", \"periodo\": \"Integral\", \"sala\": \"315\", \"predio\":[\"3\"]}";

    // Cria um objeto HorarioAtendimento a partir do JSON
    HorarioAtendimento horario = new HorarioAtendimento(json);

    // Verifica se o prédio correto foi atribuído ao objeto HorarioAtendimento
    assertEquals(3, horario.getPredio());
}

@Test
public void testCriarHorarioAtendimentoComInformacoesFaltando() {
    String json = "{\n" +
            "\"nomeDoProfessor\": \"João da Silva\",\n" +
            "\"predio\": [\"2\"]\n" +
            "}";
    HorarioAtendimento horario = null;
    try {
        horario = new HorarioAtendimento(json);
    } catch (JSONException e) {
        fail("Erro ao criar objeto HorarioAtendimento com informações faltando");
    }
    assertEquals("João da Silva", horario.getNomeProfessor());
    assertNull(horario.getHorarioAtendimento());
    assertNull(horario.getPeriodo());
    assertNull(horario.getSala());
    assertEquals(2, horario.getPrédio());
}

@Test(expected = JSONException.class)
public void testCriarHorarioAtendimentoComJsonInvalido() {
String jsonInvalido = "{ nomeDoProfessor: "Prof. Fulano", horarioDeAtendimento: "10:00 - 12:00", periodo: "Integral", sala: 123, predio: [1, 2, 3] }";
HorarioAtendimento ha = new HorarioAtendimento(jsonInvalido);
}

@Test
public void testCriarHorarioAtendimentoComJsonVazio() {
    String json = "{}";
    HorarioAtendimento horario = null;
    try {
        horario = new HorarioAtendimento(json);
    } catch (JSONException e) {
        fail("Erro ao criar objeto HorarioAtendimento com JSON vazio.");
    }
    assertNotNull("Objeto HorarioAtendimento não deve ser nulo.", horario);
}

@Test
public void testObtemHorarioDeAtendimentoPorPeriodo() {
    String json = "{\"nomeDoProfessor\": \"Fulano de Tal\", \"horarioDeAtendimento\": \"10:00 - 12:00\", \"periodo\": \"Matutino\", \"sala\": \"B205\", \"predio\":[\"2\",\"6\"]}";
    HorarioAtendimento horario = new HorarioAtendimento(json);
    
    String horarioMatutino = horario.getHorarioDeAtendimentoPorPeriodo("Matutino");
    assertEquals("10:00 - 12:00", horarioMatutino);
    
    String horarioVespertino = horario.getHorarioDeAtendimentoPorPeriodo("Vespertino");
    assertNull(horarioVespertino);
}


    @Test
    public void testCriarHorarioAtendimentoComJSONNulo() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            HorarioAtendimento ha = new HorarioAtendimento(null);
        });
    }

    @Test
    public void testCriarHorarioAtendimentoComInformacaoFaltandoNoJSON() {
        String json = "{\"nomeDoProfessor\": \"Fulano\", \"horarioDeAtendimento\": \"14h às 16h\", \"periodo\": \"integral\", \"sala\": \"102\"}";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            HorarioAtendimento ha = new HorarioAtendimento(json);
        });
    }

    @Test
    public void testCriarHorarioAtendimentoComFormatoInvalidoNoJSON() {
        String json = "{\"nomeDoProfessor\": \"Fulano\", \"horarioDeAtendimento\": \"14h às 16h\", \"periodo\": \"integral\", \"sala\": \"102\", \"predio\":[\"1\",\"2\",\"3\",\"4\",\"6\"], \"outraInfo\": \"valor\"}";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            HorarioAtendimento ha = new HorarioAtendimento(json);
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCriarHorarioAtendimentoComPeriodoInvalido() {
        horarioAtendimento = new HorarioAtendimento("João", "Segunda-feira 13h-14h", "manhã", "Sala A1", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCriarHorarioAtendimentoComSalaInvalida() {
        horarioAtendimento = new HorarioAtendimento("João", "Segunda-feira 13h-14h", "integral", "Sala Z", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCriarHorarioAtendimentoComPredioInvalido() {
        horarioAtendimento = new HorarioAtendimento("João", "Segunda-feira 13h-14h", "integral", "Sala A1", 5);
    }

    @Test
    public void testCriarHorarioAtendimentoComJsonInvalido() {
        String json = "{nomeDoProfessor: 'Professor X', horarioDeAtendimento: '10:00-12:00', periodo: 'integral', sala: '123', predio: [1, 2]}";
        assertThrows(IllegalArgumentException.class, () -> {
            HorarioAtendimento ha = new HorarioAtendimento(json);
        });
    }
    
    @Test
    public void testCriarHorarioAtendimentoComInformacoesFaltandoEPrédioInvalido() {
        String json = "{nomeDoProfessor: 'Professor X', periodo: 'integral', predio: [1, 2]}";
        assertThrows(IllegalArgumentException.class, () -> {
            HorarioAtendimento ha = new HorarioAtendimento(json);
        });
    }
    
    @Test
    public void testCriarHorarioAtendimentoComInformacoesEmFormatosInvalidosEPeriodoInvalido() {
        String json = "{nomeDoProfessor: 'Professor X', horarioDeAtendimento: '10h00-12h00', periodo: 'manhã', sala: 'abc', predio: [1, 2]}";
        assertThrows(IllegalArgumentException.class, () -> {
            HorarioAtendimento ha = new HorarioAtendimento(json);
        });
    }
    
    @Test
    public void testCriarHorarioAtendimentoComSalaDeAtendimentoInvalidaEPrédioInvalido() {
        String json = "{nomeDoProfessor: 'Professor X', horarioDeAtendimento: '10:00-12:00', periodo: 'integral', sala: '1234', predio: [0, 11]}";
        assertThrows(IllegalArgumentException.class, () -> {
            HorarioAtendimento ha = new HorarioAtendimento(json);
        });
    }

    




}
