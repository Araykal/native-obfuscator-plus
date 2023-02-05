package by.radioegor146;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.zip.ZipOutputStream;

public class Util {

    public static void createClinit(final ClassNode classNode) {
        final MethodNode methodNode = new MethodNode(Opcodes.ACC_STATIC | Opcodes.ACC_FINAL, "<clinit>", "()V", null, null);
        InsnList instructions = new InsnList();
        //System.out.println("正在为主类静态代码块添加库调用");
        LabelNode labelNode11 = new LabelNode();
        instructions.add(labelNode11);
        instructions.add(new LdcInsnNode("native"));
        instructions.add(new LdcInsnNode(".dll"));
        instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/io/File", "createTempFile", "(Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;", false));
        instructions.add(new VarInsnNode(Opcodes.ASTORE, 1));
        instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
        instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/File", "deleteOnExit", "()V", false));
        instructions.add(new TypeInsnNode(Opcodes.NEW, "java/io/FileOutputStream"));
        instructions.add(new InsnNode(Opcodes.DUP));
        instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
        instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/io/FileOutputStream", "<init>", "(Ljava/io/File;)V", false));
        instructions.add(new VarInsnNode(Opcodes.ASTORE, 2));
        LabelNode labelNode3 = new LabelNode();
        instructions.add(labelNode3);
        instructions.add(new IntInsnNode(Opcodes.SIPUSH, 1024));
        instructions.add(new IntInsnNode(Opcodes.NEWARRAY, Opcodes.T_BYTE));
        instructions.add(new VarInsnNode(Opcodes.ASTORE, 5));
        instructions.add(new LdcInsnNode(Type.getType("L" + classNode.name + ";")));
        instructions.add(new LdcInsnNode("os.arch"));
        instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/System", "getProperty", "(Ljava/lang/String;)Ljava/lang/String;"));
        instructions.add(new LdcInsnNode("64"));
        instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "contains", "(Ljava/lang/CharSequence;)Z"));
        instructions.add(new VarInsnNode(Opcodes.ISTORE, 0));

        if (!Main.configManager.getConfig().isUrlLoad()) {
            instructions.add(new LdcInsnNode("/" + Main.configManager.getConfig().getCustomLoadPackage() + "/" + Main.configManager.getConfig().getProjectName() + (Main.configManager.getConfig().getCustomSuffix().equals("none") ? "" : "." + Main.configManager.getConfig().getCustomSuffix())));
            instructions.add(new VarInsnNode(Opcodes.ASTORE, 6));
            instructions.add(new VarInsnNode(Opcodes.ILOAD, 0));
            LabelNode labelNode4 = new LabelNode();
            instructions.add(new JumpInsnNode(Opcodes.IFNE, labelNode4));
            instructions.add(new LdcInsnNode("/" + Main.configManager.getConfig().getCustomLoadPackage() + "/" + Main.configManager.getConfig().getProjectName() + (Main.configManager.getConfig().getCustomSuffix().equals("none") ? "" : "." + Main.configManager.getConfig().getCustomSuffix())));
            instructions.add(new VarInsnNode(Opcodes.ASTORE, 6));
            instructions.add(labelNode4);
            instructions.add(new VarInsnNode(Opcodes.ALOAD, 6));
            instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getResourceAsStream", "(Ljava/lang/String;)Ljava/io/InputStream;", false));
            instructions.add(new VarInsnNode(Opcodes.ASTORE, 7));
        } else {
            instructions.add(new TypeInsnNode(Opcodes.NEW," java/net/URL"));
            instructions.add(new InsnNode(Opcodes.DUP));
            instructions.add(new LdcInsnNode(Main.configManager.getConfig().getUrl()));
            instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,"java/net/URL","<init>","(Ljava/lang/String;)V"));
            instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,"java/net/URL","openConnection","()Ljava/net/URLConnection;"));
            instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,"java/net/URLConnection","getInputStream","()Ljava/io/InputStream;"));
            instructions.add(new VarInsnNode(Opcodes.ASTORE, 7));
        }

        LabelNode labelNode15 = new LabelNode();
        LabelNode labelNode14 = new LabelNode();
        instructions.add(labelNode14);
        instructions.add(new VarInsnNode(Opcodes.ALOAD, 7));
        instructions.add(new VarInsnNode(Opcodes.ALOAD, 5));
        instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/InputStream", "read", "([B)I", false));
        instructions.add(new InsnNode(Opcodes.DUP));
        instructions.add(new VarInsnNode(Opcodes.ISTORE, 4));
        instructions.add(new JumpInsnNode(Opcodes.IFLE, labelNode15));
        instructions.add(new VarInsnNode(Opcodes.ALOAD, 2));
        instructions.add(new VarInsnNode(Opcodes.ALOAD, 5));
        instructions.add(new InsnNode(Opcodes.ICONST_0));
        instructions.add(new VarInsnNode(Opcodes.ILOAD, 4));
        instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/FileOutputStream", "write", "([BII)V", false));
        instructions.add(new VarInsnNode(Opcodes.ALOAD, 2));
        instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/FileOutputStream", "flush", "()V", false));
        instructions.add(new JumpInsnNode(Opcodes.GOTO, labelNode14));
        instructions.add(labelNode15);
        instructions.add(new VarInsnNode(Opcodes.ALOAD, 7));
        instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/InputStream", "close", "()V", false));
        instructions.add(new VarInsnNode(Opcodes.ALOAD, 2));
        instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/FileOutputStream", "close", "()V", false));
        instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
        LabelNode labelNode20 = new LabelNode();
        instructions.add(labelNode20);
        LabelNode labelNode12 = new LabelNode();
        instructions.add(new JumpInsnNode(Opcodes.GOTO, labelNode12));

        LabelNode labelNode21 = new LabelNode();
        instructions.add(labelNode21);
        instructions.add(new InsnNode(Opcodes.DUP));
        instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Exception", "printStackTrace", "()V", false));
        instructions.add(new InsnNode(Opcodes.ATHROW));

        instructions.add(labelNode12);

        instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/File", "getAbsolutePath", "()Ljava/lang/String;", false));
        instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                "java/lang/System",
                "load",
                "(Ljava/lang/String;)V",
                false));

        instructions.add(new InsnNode(Opcodes.RETURN));

        TryCatchBlockNode tryCatchBlockNode = new TryCatchBlockNode(labelNode11, labelNode20, labelNode21, "java/lang/Exception");
        methodNode.tryCatchBlocks.add(tryCatchBlockNode);
        methodNode.instructions.add(instructions);

        //System.out.println("添加完毕");
        classNode.methods.add(methodNode);
    }

    public static boolean isObfMethod(MethodNode methodNode) {
        List<AnnotationNode> visibleAnnotations = methodNode.visibleAnnotations;
        if (visibleAnnotations != null) {
            for (AnnotationNode visibleAnnotation : visibleAnnotations) {
                List<Object> values = visibleAnnotation.values;
                if (values != null) {
                    if (values.get(1) instanceof String) {
                        String str = (String) values.get(1);
                        return str.equals("MethodObf");
                    }
                }
            }
        }
        return false;
    }

    public static boolean isObfClass(ClassNode classNode) {
        List<AnnotationNode> visibleAnnotations = classNode.visibleAnnotations;
        if (visibleAnnotations != null) {
            for (AnnotationNode visibleAnnotation : visibleAnnotations) {
                List<Object> values = visibleAnnotation.values;
                if (values != null) {
                    if (values.get(1) instanceof String) {
                        String str = (String) values.get(1);
                        return str.equals("ClassObf");
                    }
                }
            }
        }
        return false;
    }

    public static boolean isClassInclude(String className) {
        List<String> classIncludes = Main.configManager.getConfig().getIncludes();
        for (String classInclude : classIncludes) {
            if (classInclude.endsWith("*")) {
                if (className.contains(classInclude.split("\\*")[0])) {
                    return true;
                }
            } else {
                if (className.equals(classInclude)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isClassExclude(String className) {
        List<String> classExcludes = Main.configManager.getConfig().getExcludes();
        for (String classExclude : classExcludes) {
            if (classExclude.endsWith("*")) {
                if (className.contains(classExclude.split("\\*")[0])) {
                    return true;
                }
            } else {
                if (className.equals(classExclude)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean getFlag(int value, int flag) {
        return (value & flag) > 0;
    }

    public static Map<String, String> createMap(Object... parts) {
        HashMap<String, String> tokens = new HashMap<>();
        for (int i = 0; i < parts.length; i += 2) {
            tokens.put(parts[i].toString(), parts[i + 1].toString());
        }
        return tokens;
    }

    private static String replaceTokens(String string, Map<String, String> tokens, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(string);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, Matcher.quoteReplacement(tokens.get(matcher.group(1))));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    public static String dynamicFormat(String string, Map<String, String> tokens) {
        String patternString = String.format("\\$(%s)",
                tokens.keySet().stream().map(Util::unicodify).collect(Collectors.joining("|")));
        return replaceTokens(string, tokens, patternString);
    }

    public static String dynamicRawFormat(String string, Map<String, String> tokens) {
        if (tokens.isEmpty()) {
            return string;
        }
        String patternString = String.format("(%s)",
                tokens.keySet().stream().map(Util::unicodify).collect(Collectors.joining("|")));
        return replaceTokens(string, tokens, patternString);
    }

    @SuppressWarnings("unchecked")
    public static <T> Stream<T> reverse(Stream<T> input) {
        Object[] temp = input.toArray();
        return (Stream<T>) IntStream.range(0, temp.length)
                .mapToObj(i -> temp[temp.length - i - 1]);
    }

    public static String readResource(String filePath) {
        try (InputStream in = NativeObfuscator.class.getClassLoader().getResourceAsStream(filePath)) {
            return writeStreamToString(in);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void copyResource(String from, Path to) throws IOException {
        try (InputStream in = NativeObfuscator.class.getClassLoader().getResourceAsStream(from)) {
            Objects.requireNonNull(in, "Can't copy resource " + from);
            Files.copy(in, to.resolve(Paths.get(from).getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static String writeStreamToString(InputStream stream) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            transfer(stream, baos);
            return new String(baos.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void writeEntry(JarFile f, ZipOutputStream out, JarEntry e) throws IOException {
        out.putNextEntry(new JarEntry(e.getName()));
        try (InputStream in = f.getInputStream(e)) {
            transfer(in, out);
        }
        out.closeEntry();
    }

    public static void writeEntry(ZipOutputStream out, String entryName, byte[] data) throws IOException {
        out.putNextEntry(new JarEntry(entryName));
        out.write(data, 0, data.length);
        out.closeEntry();
    }

    static void transfer(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[4096];
        for (int r = in.read(buffer, 0, 4096); r != -1; r = in.read(buffer, 0, 4096)) {
            out.write(buffer, 0, r);
        }
    }

    public static String escapeCppNameString(String value) {
        Matcher m = Pattern.compile("([^a-zA-Z_0-9])").matcher(value);
        StringBuffer sb = new StringBuffer(value.length());
        while (m.find()) {
            m.appendReplacement(sb, String.valueOf((int) m.group(1).charAt(0)));
        }
        m.appendTail(sb);
        String output = sb.toString();
        if (output.length() > 0 && (output.charAt(0) >= '0' && output.charAt(0) <= '9')) {
            output = "_" + output;
        }
        return output;
    }

    private static String unicodify(String string) {
        StringBuilder result = new StringBuilder();
        for (char c : string.toCharArray()) {
            result.append("\\u").append(String.format("%04x", (int) c));
        }
        return result.toString();
    }

    public static int byteArrayToInt(byte[] b) {
        if (b.length == 4) {
            return b[0] << 24 | (b[1] & 0xff) << 16 | (b[2] & 0xff) << 8 | (b[3] & 0xff);
        } else if (b.length == 2) {
            return (b[0] & 0xff) << 8 | (b[1] & 0xff);
        }

        return 0;
    }
}
