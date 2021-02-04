package net.dzikoysk.funnyguilds.element.notification;

import com.google.common.collect.Lists;
import me.stevimeister.combat.CombatPlugin;
import net.dzikoysk.funnyguilds.util.nms.PacketCreator;
import net.dzikoysk.funnyguilds.util.nms.Reflections;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public final class NotificationUtil {

    private static final Class<?> PACKET_PLAY_OUT_TITLE_CLASS;
    private static final Class<?> PACKET_PLAY_OUT_CHAT_CLASS;
    private static final Class<?> TITLE_ACTION_CLASS;
    private static final Class<?> CHAT_MESSAGE_TYPE_CLASS;

    private static final Method CREATE_BASE_COMPONENT_NMS;
    private static final Method CREATE_BASE_COMPONENT_CRAFTBUKKIT;
    private static final Method GET_TITLE_ACTION_ENUM;

    private static final Enum<?> TITLE_ENUM;
    private static final Enum<?> SUBTITLE_ENUM;
    private static final Enum<?> TIMES_ENUM;

    private static final String BASE_COMPONENT_JSON_PATTERN = "{\"text\": \"{TEXT}\"}";

    private static final UUID SENDER_ALWAYS_DISPLAY = new UUID(0L, 0L);

    static {
        PACKET_PLAY_OUT_TITLE_CLASS = Reflections.getNMSClass("PacketPlayOutTitle");
        PACKET_PLAY_OUT_CHAT_CLASS = Reflections.getNMSClass("PacketPlayOutChat");
        TITLE_ACTION_CLASS = "v1_8_R1".equals(Reflections.SERVER_VERSION) ? Reflections.getNMSClass("EnumTitleAction") : Reflections.getNMSClass("PacketPlayOutTitle$EnumTitleAction");

        if (Reflections.USE_PRE_12_METHODS) {
            CHAT_MESSAGE_TYPE_CLASS = null;
        } else {
            CHAT_MESSAGE_TYPE_CLASS = Reflections.getNMSClass("ChatMessageType");
        }

        CREATE_BASE_COMPONENT_NMS = "v1_8_R1".equals(Reflections.SERVER_VERSION) ? Reflections.getMethod(Reflections.getNMSClass("ChatSerializer"), "a", String.class) : Reflections.getMethod(Reflections.getNMSClass("IChatBaseComponent$ChatSerializer"), "a", String.class);
        CREATE_BASE_COMPONENT_CRAFTBUKKIT = Reflections.getMethod(Reflections.getCraftBukkitClass("util.CraftChatMessage"), "fromString", String.class, boolean.class);
        GET_TITLE_ACTION_ENUM = Reflections.getMethod(TITLE_ACTION_CLASS, "valueOf", String.class);

        try {
            TITLE_ENUM = (Enum<?>) GET_TITLE_ACTION_ENUM.invoke(null, "TITLE");
            SUBTITLE_ENUM = (Enum<?>) GET_TITLE_ACTION_ENUM.invoke(null, "SUBTITLE");
            TIMES_ENUM = (Enum<?>) GET_TITLE_ACTION_ENUM.invoke(null, "TIMES");
        }
        catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException("Could not retrieve title packet enums", ex);
        }
    }

    public static List<Object> createTitleNotification(String text, String subText, int fadeIn, int stay, int fadeOut) {
        List<Object> packets = Lists.newArrayList();

        Object titlePacket = PacketCreator.of(PACKET_PLAY_OUT_TITLE_CLASS)
                .create()
                .withField("a", TITLE_ENUM)
                .withField("b", createBaseComponent(text, false))
                .getPacket();

        Object subtitlePacket = PacketCreator.of(PACKET_PLAY_OUT_TITLE_CLASS)
                .create()
                .withField("a", SUBTITLE_ENUM)
                .withField("b", createBaseComponent(subText, false))
                .getPacket();

        Object timesPacket = PacketCreator.of(PACKET_PLAY_OUT_TITLE_CLASS)
                .create()
                .withField("a", TIMES_ENUM)
                .withField("c", fadeIn)
                .withField("d", stay)
                .withField("e", fadeOut)
                .getPacket();

        packets.addAll(Arrays.asList(titlePacket, subtitlePacket, timesPacket));
        return packets;
    }

    public static Object createBaseComponent(String text, boolean keepNewLines) {
        String text0 = text != null ? text : "";

        try {
            return keepNewLines ? Array.get(CREATE_BASE_COMPONENT_CRAFTBUKKIT.invoke(null, text0, true), 0) : CREATE_BASE_COMPONENT_NMS.invoke(null, StringUtils.replace(BASE_COMPONENT_JSON_PATTERN, "{TEXT}", text0));
        } catch (IllegalAccessException | InvocationTargetException ex) {
            CombatPlugin.getInstance().getLogger().info("Could not create base component");
            return null;
        }
    }

    public static Object createActionbarNotification(String text) {
        Object actionbarPacket;

        if (CHAT_MESSAGE_TYPE_CLASS != null) {
            PacketCreator packetCreator = PacketCreator.of(PACKET_PLAY_OUT_CHAT_CLASS)
                    .create()
                    .withField("a", createBaseComponent(text, false))
                    .withField("b", CHAT_MESSAGE_TYPE_CLASS.getEnumConstants()[2]);

            switch (Reflections.SERVER_VERSION) {
                case "v1_16_R1":
                case "v1_16_R2":
                case "v1_16_R3":
                    // We always want to display our action bar notification (and it only applies to 1.16+)
                    packetCreator.withField("c", SENDER_ALWAYS_DISPLAY);
                    break;
                default:
                    break;
            }

            actionbarPacket = packetCreator.getPacket();
        }
        else {
            actionbarPacket = PacketCreator.of(PACKET_PLAY_OUT_CHAT_CLASS)
                    .create()
                    .withField("a", createBaseComponent(text, false))
                    .withField("b", (byte) 2)
                    .getPacket();
        }

        return actionbarPacket;
    }

    private NotificationUtil() {}
}