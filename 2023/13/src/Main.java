import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

// Beautiful solution for that puzzle (not from me)
// https://mutraction.dev/link/fe
// https://mutraction.dev/sandbox/?view=normal#7Vptb9s2EP7uX3G1gUJaHSl296FIZBdFu2HFhq5Y+y0LMEaiLCaSKIhUOi/1f9+RlETJkp12yF6wVkAimTzey3PH050klhW8lHAHsiThzRzefSBFQcs50DimoZzD97z8joQJ7CAueQbTrFKUkvH8JOLZ9HwyCXkuJAjJS7KhP9ItrGBKeHjC8qKSliDjEU1xTgty7iaAhyY5A0GFQIbvDAtvQ+VrSTPHsnTh40fA4Vc0JlUqX6tljjuH+vB9uCVpRZEfyISCpL9LUlKiZaA9KaPRayNqOp0r8krQCGJeQkjSsEqJsmcOBNXY5DjDcwhTFt5oBmWVvyUlrj2dW3kFjsACkMPyuaYSKFH+UuVOoWnzKruipQvGTnVo+72aGeKg6M73Zru6IokZ1CAZyt18snMR0bjKtQsUE0ENHA2WsmT5xm0uLi4vLmslSiqrMjeYeziZOa4nUJ50/F/zX3Pf9TJSOBms1pDZCd9FgbuOyNqChdMYZ9xbEClpmQtjWaPU0Cz3vLMoY2XJS3GG8RexkmoBjeZzKLhgZsSgCTu0ZQUXl4ZFSiVILokKqlMERY0VZ9qrTk8n4HGrXtcjmlJxYchhcY6noKHzUppvZIJjT550l+igjZF/QsObH3jJ/uA5auDUy+bA3H1y7V1jp1dUInF6tk6Tlsm0azGDnXs+4GPMfYLanp7CN8CGFGi4ZDluhaI/t5vYq0mHGFHCDZkLlIxxv7I/GpM6WozhZRd/LmR25V9A7ZaWkoWfh9lfRKuDWbsHDrjfbro5lPzDWBboYYg0cAKLOVybHwrSNQYzPH6MQyPBeHKCtOPw1rQX7BIerVbN0ovrS7fZ+DFJBT2fDG2s52VZ1dP75g6CwhraSTQdpUxglVRgskbTXpQl2bYanl7WBrlezNLUmU73Y6xejujYnbtv84XneUhx6cXmBuU4oYojlb2MXIUEej10j5hsKHtG1zPBuqUNivV7vKmIKssIehy3SUnjlNYJuI6+JkUxAQECwvPN+q4OvV3g1yNe4BeW711zb22QEXPoJhI0pWdxsB7Eb1AMx9TxPkE9muyXEAGko1W9rRAfr91SHSXreRhlTKS1eJRjO9uxeqi2P6J3ELFbyKozFq8O6LjCuO7mS6xJhNymdHV3h3GTy+9JxtItZoeM51wUJKSYHiKGtzKiRtFnv+OAOr3qpJKQp1WWI7PdOJYDN6GXMPBwIw48NDCnVe8Di2SidGDyRCUdmktU5YqXES3fc7xrXWvrxoB8DlPBUxbB7NmzeAqqhDmoq9VZbZ7dYeV81G6chTuy7AB54zGKeeUTPNfm7K7f7nVP7/54wMsH8QgOmmlQGnhWwYYZ5Ci4mqlG2BNYJFIH68IRx7lqCxyVPwb1EbhbyFvwTPz8RGNV2HaD5Fh8PJTV/4TJnxB4B3Aq1m94k8k+YAKMeZVH3mjiUcdBUPqAH4m/essZjT9xEwW+/d0swbF+vf0D2yQp/smXCSkdYctjtl9ftLeuOxA2NJnyiknF1hBMerxUdRSyPDm52rgqG2FftikVTu2EaqTc2jzl6U5WtzIYPAFYaN+vxzqFZdspyG1B4V1WRRv6tklvK5OsGlvmSjVb8Ndl/T/WYsxBaPXO9tX8b7QehreuPnHdkSakX8625fcD9SYWJaPK/6lVOQ5xv2n5DJT39TfhZaNfL/Qw9usNYH7rlDJE7rMbI+uwgQb/0ZZpsAE/AiYmGjP1eMbCq1xsTPtFpWt4Q940tr7kqRk4EBUP1XzV+52BbboYGlNcQ7cL64PVKnKjEwieUB5rRd0MpTQBVrCLm7rDu8arMbKG9BETaL3TwuM2neDQqerowjjid0tigL0ZkuyOBsS9ajVboZ2pt4KVuvvann5tT//+9tSoSOvAsxxR6aQpxvAm8mW2tccbz561g57WAGruauiXfmWrhQ+J0Y7724quaN2N/luNt0H2ePOtlT1uT7cx6B5fdnPeic97etX7nfQvBuq+dx7kWYJmeiBq1PH1WcN9Jn/BzxpMQYMtNBZDZqJTXiSLPUYEEqwwVtNEykKc+b59I+xF9NYPiaDCJzxcni6f+tP1i59fgroMfLIGH47zItEtZmgeh9jU447KfM0kIlt/8XS6fkW2sNCMrHp+V78gJVc0XZt3vYFvftnZ5r2weVus8qF9wYp5D70stnn4nVJhNTWvr60/sBjD6qrfYiVUZQUce0Yz5RnogBxcVVJiesUkSq5SGu2J01lXZVue61fNOO3ozGaI2tfJ6sHKGi/qN86Bb9g+uJxlT87SyrGCRH3DaCHZlCx6qesMhGDhf6shaO81/ffdqMYC9lJFkDxdv63Nwsve3F3zKYLTvHDuBjpWb0aZ9cOptzyo3vIT1FseUU/vs0nEwyrD0PKueLRVD6xoHjl4Ui/0zdcWjvHM3ucQYvA5xLzrYbf3QcDgI4n+s8HfJjNvNvPU3wT/qzNeqRF1zDpXdtZrV8zasYmi08P6ypvVSzShYjObzcyS9qoz2674rf4OAbPQnw==
public class Main {
  public static void main(String[] args) throws Exception {
//    runForInput("./input_0.txt");
    runForInput("./input_1.txt");
  }

  private static void runForInput(@SuppressWarnings("SameParameterValue") String inputFileName) throws Exception {
    System.out.println("\r\nInput: " + inputFileName);

    Task task = new Task();
    // Task2 task = new Task2();
    task.init();

    Instant t1 = Instant.now();

    try (Stream<String> lines = Files.lines(new File(inputFileName).toPath(), StandardCharsets.UTF_8)) {
      lines.forEach(task::addLine);
    }

    task.afterParse();

    Instant t2 = Instant.now();

    System.out.println("Duration: " + Duration.between(t1, t2).toMillis() + "ms");
  }
}