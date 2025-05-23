package AoT.AoT;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

public class App {
	
	private static String uri = null;
    private static String user = null;
    private static String password = null;
    private static String filePathInstructions = "data/neo4j/instructions.txt";
    private static String filePathDataBase = "data/neo4j/credentials.txt";
    private static String filePathQueries = "data/neo4j/queries.txt";
    
    private static List<String> instructions = new ArrayList<String>();
    
    private static Map<String, List<Query>> queryMap = new HashMap<>();
    private static List<String> categories = new ArrayList<>();
    
    private static String GO_OUT = "s";
    private static int RETURN_TO_MAINMENU = -1;
	
    public static void main(String[] args) {
    	
        // Leemos el archivo con las credenciales de la base de datos
        readCredentials();

        // Conectar a Neo4j y ejecutar las consultas
        if (!password.equals("neo4j")) {
        	
        	// Lista para almacenar instrucciones para popularizar la base de datos
            readInstructions();

            // Mapa para almacenar consultas categorizadas
            readQueries();
            
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        	
        	try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password))) {
                try (Session session = driver.session()) {
                	
                	for (String instruction : instructions) {
                		session.run(instruction);
					}
                	
                	try (Scanner scanner = new Scanner(System.in)) {
    	            	while (true) {
    	            		
    	            		String category = mainMenu(scanner);
    						
    						if (category.toLowerCase().equals(GO_OUT)) {
    							showBye();
    							break;
    						}
    						
    						boolean flag = false;
    						for (String c : queryMap.keySet()) {
    							if (flag) break;
    							if (category.toLowerCase().equals(c.toLowerCase().substring(0, 1))) {
    								
    								flag = true;
    								while (true) {
    									
    									int query = queryMenu(scanner, c);
    									
    									if (query == RETURN_TO_MAINMENU) break;
    									if (query < RETURN_TO_MAINMENU || query > queryMap.get(c).size() || query == 0) continue;
    									
    									runQuery(session, c, query);
    									
    								}
    							}
    						}
    						
    						if (!flag) {
//    							System.err.println("ERROR: Categoría no disponible");
    						}
    						
    					}
                	}
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        	
        }
        else {
        }
        
    }
    
    ////////////////////////////////////PRIVATE METHODS//////////////////////////////////////////////////////////////

	private static void showBye() {
		System.out.println("\n\nAdiós :)");
	}

	private static void runQuery(Session session, String c, int query) {
		System.out.println("\tConsulta a ejecutar: " + queryMap.get(c).get(query-1).getQuery());
		System.out.println("\tResultados:");
		try {
            Result result = session.run(queryMap.get(c).get(query-1).getQuery());
            int counter = 0;
            while (result.hasNext()) {
            	counter++;
                Record record = result.next();
                System.out.println("\t\t" + record.asMap());
            }
            System.out.println("\t-----\n\t\t--> Nº de resultados: " + counter + "\n\t-----\n\n");
        } catch (Exception e) {
            System.err.println("ERROR: ejecutar consulta para categoría: " + c);
            e.printStackTrace();
        }
	}

	private static int queryMenu(Scanner scanner, String c) {
		System.out.println("Consultas " + c + ":");
		int index = 0;
		for (Query query : queryMap.get(c)) {
			System.out.print("\t\t" + ++index + ".");
			System.out.println(" " + query.getDescription() + "\n");
		}
		
		System.out.print("Elige una consulta (o -1 para volver): ");
		int query = -2;
		try {
			query = scanner.nextInt();
		} catch (InputMismatchException e) {}
		scanner.nextLine();
		
		return query;
	}

	private static String mainMenu(Scanner scanner) {
        System.out.println("Categorías de Consultas:");
        for (String category : categories) {
        	System.out.print("\t>>");
			System.out.println(String.format(" %s (%s/%s)", category, Character.toUpperCase(category.charAt(0)), Character.toLowerCase(category.charAt(0))));
		}
        
		System.out.print("Elige una categoría (o \"s/S\" para salir): ");
		String category = scanner.nextLine();
		
		return category;
	}

	private static void readQueries() {
		System.out.println("Loading Queries . . .");
		
		// Leer el archivo y llenar el mapa
        try (BufferedReader br = new BufferedReader(new FileReader(filePathQueries))) {
            String line;
            String currentCategory = null;
            String description = null;
            StringBuilder currentQuery = new StringBuilder();

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (line.endsWith(":")) {
                    // Nueva categoría
                    currentCategory = line.substring(0, line.length() - 1);
                    queryMap.put(currentCategory, new ArrayList<>());
                    categories.add(currentCategory);
                } else if (line.startsWith("[") && currentQuery.length() == 0) {
                    // Nueva descripción
                    description = line.substring(1, line.lastIndexOf("]"));
                } else if (currentCategory != null) {
                    // Parte de una consulta
                    currentQuery.append(line).append(" ");
                    if (line.endsWith(";")) {
                        // Fin de consulta
                        queryMap.get(currentCategory).add(new Query(description, currentQuery.toString().trim()));
                        currentQuery.setLength(0); // Limpiar para la próxima consulta
                        description = null; // Reiniciar descripción
                    }
                }
            }

            // Agregar cualquier consulta no finalizada (en caso de que falte el punto y coma)
            if (currentQuery.length() > 0 && currentCategory != null) {
                queryMap.get(currentCategory).add(new Query(description, currentQuery.toString().trim()));
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + filePathQueries);
            e.printStackTrace();
        }
	}

	private static void readInstructions() {
		System.out.println("Initializing Data . . .");
		
		//Agregamos una instrucción para limpiar todo el grafo
		instructions.add("MATCH (n) DETACH DELETE n;");
        // Leer el archivo de instrucciones para popularizar la base de datos
        try (BufferedReader br = new BufferedReader(new FileReader(filePathInstructions))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("//")) {
                    continue;
                }
                
                instructions.add(line.substring(0, line.length()));
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + filePathInstructions);
            e.printStackTrace();
        }
	}

	private static void readCredentials() {
		System.out.println("Verifying Credentials . . .");
		try (BufferedReader br = new BufferedReader(new FileReader(filePathDataBase))) {
            String line;
            String var = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (line.contains("=")) {
                    var = line.substring(line.indexOf("=") + 1, line.length());
                    
                    if (uri == null) {
                    	uri = var;
                    }
                    else if (user == null) {
                    	user = var;
                    }
                    else if (password == null) {
                    	password = var;
                    }
                }
            }
            if (password.equals("neo4j")) {
            	System.out.println("Modify the data/neo4j/credentials.txt file, setting your credentials correctly");
            }
        } catch (IOException e) {
        	if (uri == null && user == null && password == null) {
                System.err.println("Error leyendo el archivo: " + filePathDataBase);
                e.printStackTrace();
        	}
        }
		
	}
}

class Query {
	
    private String description;
    private String query;

    public Query(String description, String query) {
        this.description = description;
        this.query = query;
    }

    public String getDescription() {
        return description;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", description, query);
    }
    
}

class Colors {
	
    // Reset
    public static final String RESET = "\033[0m";

    // Colores normales
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String MAGENTA = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    // Colores en negrita
    public static final String BLACK_BOLD = "\033[1;30m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String YELLOW_BOLD = "\033[1;33m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String MAGENTA_BOLD = "\033[1;35m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String WHITE_BOLD = "\033[1;37m";

    // Colores subrayados
    public static final String BLACK_UNDERLINED = "\033[4;30m";
    public static final String RED_UNDERLINED = "\033[4;31m";
    public static final String GREEN_UNDERLINED = "\033[4;32m";
    public static final String YELLOW_UNDERLINED = "\033[4;33m";
    public static final String BLUE_UNDERLINED = "\033[4;34m";
    public static final String MAGENTA_UNDERLINED = "\033[4;35m";
    public static final String CYAN_UNDERLINED = "\033[4;36m";
    public static final String WHITE_UNDERLINED = "\033[4;37m";
    
}
