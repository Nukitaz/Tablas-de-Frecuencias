import java.util.Arrays;

public class TablaDeFrecuencias {
    public static void main(String[] args) {
        int[] dataset = {
            105, 106, 105, 107, 109, 111, 110, 110, 107, 107, 104, 99, 103, 99, 103, 101, 100,
            101, 100, 103, 98, 92, 97, 94, 95, 95, 93, 95, 95, 95, 91, 82, 91, 85, 90, 86, 87, 89, 87, 89
        };

        // Ordenar los datos en forma ascendente
        Arrays.sort(dataset);

        // Calcular el rango
        int min = dataset[0];
        int max = dataset[dataset.length - 1];
        int rango = max - min;

        // Calcular el número de clases utilizando la fórmula de Sturges
        int n = dataset.length;
        double kExacto = 1 + 3.322 * Math.log10(n);
        int k = (int) Math.round(kExacto);

        // Calcular la amplitud del intervalo de clase
        int c = (int) Math.ceil((double) rango / k);

        // Definir límites de clases
        int[] claseLimitesInferior = new int[k];
        int[] claseLimitesSuperior = new int[k];
        for (int i = 0; i < k; i++) {
            claseLimitesInferior[i] = min + i * c;
            claseLimitesSuperior[i] = claseLimitesInferior[i] + c - 1;
        }

        // Ajuste para el último límite superior
        claseLimitesSuperior[k - 1] = max;

        // Inicializar frecuencias, puntos medios, frecuencias relativas y porcentajes
        int[] frecuencia = new int[k];
        double[] puntoMedio = new double[k];
        int[] frecuenciaAcumulada = new int[k];
        double[] frecuenciaRelativa = new double[k];
        double[] frecuenciaRelativaAcumulada = new double[k];
        double[] porcentaje = new double[k];

        // Calcular frecuencias
        for (int dato : dataset) {
            for (int i = 0; i < k; i++) {
                if (dato >= claseLimitesInferior[i] && dato <= claseLimitesSuperior[i]) {
                    frecuencia[i]++;
                    break;
                }
            }
        }

        //puntos medios, frecuencias acumuladas, frecuencias relativas y porcentajes
        frecuenciaAcumulada[0] = frecuencia[0];
        frecuenciaRelativa[0] = (double) frecuencia[0] / n;
        frecuenciaRelativaAcumulada[0] = frecuenciaRelativa[0];
        porcentaje[0] = frecuenciaRelativa[0] * 100;

        //Inicializar conteo
        double conteofrecuenciaRelativa=0;
        double conteofrecuenciaRelativaAcumulada=0;
        double conteoporcentaje=0;
        for (int i = 0; i < k; i++) {
            //punto medio
            puntoMedio[i] = claseLimitesInferior[i] + claseLimitesSuperior[i] / 2.0; //sin(), una fuente me dijo que los eliminara... "la formula indica que llevan parenstesis"
            
            // Calcular frecuencia relativa y porcentaje
            frecuenciaRelativa[i] = (double) frecuencia[i] / n;
            porcentaje[i] = frecuenciaRelativa[i] * 100;
            
            // Calcular frecuencia acumulada y relativa acumulada
            if (i > 0) {
                frecuenciaAcumulada[i] = frecuenciaAcumulada[i - 1] + frecuencia[i];
                frecuenciaRelativaAcumulada[i] = frecuenciaRelativaAcumulada[i - 1] + frecuenciaRelativa[i];
            } else {
                frecuenciaAcumulada[i] = frecuencia[i];
                frecuenciaRelativaAcumulada[i] = frecuenciaRelativa[i];
            }
        
            // Sumar al conteo total
            conteofrecuenciaRelativa += frecuenciaRelativa[i];
            conteofrecuenciaRelativaAcumulada += frecuenciaRelativa[i];
            conteoporcentaje += porcentaje[i];
        }

        // Imprimir resultados
        System.out.printf("%-10s%-20s%-15s%-15s%-20s%-20s%-25s%-10s%n", "Clases", "Límites de Clases", "Frecuencia fi", "Punto Medio (Xc)", "Fa", "Fr", "Fra", "Porcentaje (%)");
        for (int i = 0; i < k; i++) {
            System.out.printf("%-10d%-20s%-15d%-15.2f%-20d%-20.4f%-25.4f%-10.2f%n", (i + 1), claseLimitesInferior[i] + " / " + claseLimitesSuperior[i], frecuencia[i], puntoMedio[i], frecuenciaAcumulada[i], frecuenciaRelativa[i], frecuenciaRelativaAcumulada[i], porcentaje[i]);
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-20s %-15s %-15s %-10s %-10.2f %-10.2f %-15.2f%n", "TOTAL", "", "n = " + n, "", "", conteofrecuenciaRelativa,  conteofrecuenciaRelativaAcumulada, conteoporcentaje);
    }
}