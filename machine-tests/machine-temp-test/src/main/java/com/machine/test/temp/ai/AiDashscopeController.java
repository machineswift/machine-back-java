package com.machine.test.temp.ai;

import com.alibaba.cloud.ai.dashscope.audio.DashScopeAudioSpeechModel;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeAudioSpeechOptions;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeAudioTranscriptionModel;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeAudioTranscriptionOptions;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisPrompt;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisResponse;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import com.alibaba.cloud.ai.dashscope.video.DashScopeVideoModel;
import com.alibaba.cloud.ai.dashscope.video.DashScopeVideoOptions;
import com.alibaba.cloud.ai.dashscope.video.VideoPrompt;
import com.alibaba.cloud.ai.dashscope.video.VideoResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

@Slf4j
@RestController
@RequestMapping("ai/dashscope")
public class AiDashscopeController {

    @Autowired
    private DashScopeChatModel dashScopeChatModel;

    @Autowired
    private DashScopeImageModel dashScopeImageModel;

    @Autowired
    private  DashScopeVideoModel dashScopeVideoModel;

    @Autowired
    private DashScopeAudioSpeechModel dashScopeAudioSpeechModel;

    @Autowired
    private DashScopeAudioTranscriptionModel dashScopeAudioTranscriptionModel;


    @GetMapping("test")
    public void test() {
        String content = dashScopeChatModel.call("你好，我是谁");
        System.out.println(content);
    }

    /**
     * 文生图
     */
    @GetMapping("test_text2Image")
    public void testText2Image() {
        DashScopeImageOptions imageOptions = DashScopeImageOptions
                .builder()
                .withModel("wan2.5-t2i-preview")
                .build();

        ImageResponse response = dashScopeImageModel.call(
                new ImagePrompt("玄幻的一把重剑", imageOptions)
        );

        String imageUrl = response.getResult().getOutput().getUrl();
        System.out.println(imageUrl);
    }

    /**
     * 文生语音
     */
    @GetMapping("test_text2Audio")
    public void testText2Audio() {
        DashScopeAudioSpeechOptions audioSpeechOptions = DashScopeAudioSpeechOptions
                .builder()
                .model("qwen3-tts-flash")
                .build();

        SpeechSynthesisResponse response = dashScopeAudioSpeechModel.call(
                new SpeechSynthesisPrompt("大家好，我是王林。", audioSpeechOptions)
        );

        File file = new File(System.getProperty("user.dir") + "/output.mp3");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            ByteBuffer byteBuffer = response.getResult().getOutput().getAudio();
            fos.write(byteBuffer.array());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 文生图
     */
    @GetMapping("test_text2Video")
    public void testText2Video() {
        DashScopeVideoOptions videoOptions = DashScopeVideoOptions
                .builder()
                .model("wan2.5-t2v-preview")
                .duration(30)
                .size("1280x720")
                .build();

        VideoResponse response = dashScopeVideoModel.call(
                new VideoPrompt("""
                        《狼群》小说 作者：刺血
                         题材：军事、雇佣兵、热血、成长
                        
                        第一章
                        
                        我从来没想过人生会这样充满意外。
                        
                        本来只是一次普通的暑期旅游，跟着几个同学来云南边境体验一下“异域风情”，谁知道会碰上这档子事。
                        
                        那天，我们脱离了旅行团，自己租了辆车想往更深的山里钻，据说那里有个未经开发、风景绝美的山谷。司机是个本地人，黑黑瘦瘦的，话不多，只是在我们坚持要去的时候，眼神里闪过一丝犹豫，但最终还是被我们加的钱打动了。
                        
                        路越来越窄，越来越颠簸，周围的植被也越发茂密原始。就在我们被颠得七荤八素，开始后悔这个决定的时候，意外发生了。
                        
                        不是车祸，是比车祸更可怕的事情。
                        
                        我们的车被拦住了。拦路的不是警察，也不是村民，而是一群拿着AK-47，面色凶狠的武装分子。那一刻，我脑子一片空白，电影里的绑架、撕票画面瞬间充斥了整个脑海。
                        
                        “下……下车！”同学里有人颤抖着喊道。
                        
                        我们全都抱着头，哆哆嗦嗦地下了车。那个司机早就双手抱头跪在一边，用我们听不懂的方言快速地说着什么，似乎在求饶。
                        
                        武装分子中一个像是头目的人走过来，用生硬的中文问：“中国人？”
                        
                        我们忙不迭地点头。
                        
                        他咧嘴笑了笑，露出一口黄牙，那笑容里没有丝毫温度。“很好，有钱。”
                        
                        我们被搜走了所有值钱的东西，钱包、手机、相机……就在我们以为破财消灾，他们准备放我们走的时候，那个头目却挥了挥手，示意手下把我们押走。
                        
                        “去……去哪？”一个女同学带着哭腔问。
                        
                        没人回答她。一个武装分子用枪托狠狠杵了我一下，示意我闭嘴跟上。
                        
                        我的心沉到了谷底。这不是抢劫，这是绑架！
                        
                        我们被押着在密林里深一脚浅一脚地走了不知道多久，天色渐渐暗了下来。恐惧和疲惫折磨着每一个人。我脑子里飞速运转，想着各种逃跑的可能性，但看着对方手里那黑黝黝的枪口，任何念头都显得那么不切实际。
                        
                        就在我们几乎绝望的时候，前方突然传来了几声极其轻微、但又异常尖锐的声响。
                        
                        “噗！噗！”
                        
                        像是装了消音器的枪声。
                        
                        紧接着，押着我们的两个武装分子一声不吭地栽倒在地，额头上多了一个恐怖的血洞。
                        
                        “敌袭！”有人用当地语言大喊。
                        
                        场面瞬间大乱。枪声骤然变得密集，但奇怪的是，大部分都是从我们来的方向射来的，精准而致命。那些武装分子像被割倒的麦子一样纷纷倒下，他们甚至看不清敌人在哪。
                        
                        我们全都吓傻了，趴在地上一动不敢动。
                        
                        混乱中，我看到几个身影如同鬼魅般在树林中穿梭。他们穿着混杂、看不出制式的丛林作战服，脸上涂着油彩，动作迅捷、安静，配合默契。他们的枪口每一次闪烁，都必然有一个武装分子倒下。
                        
                        这根本不是战斗，这是一场屠杀，一场单方面的、高效率的清除。
                        
                        战斗在几分钟内就结束了。十几个武装分子无一活口。
                        
                        那几个人开始默默地检查尸体，不时在一些尸体上补上一枪。其中一个身材格外高大魁梧的人向我们走来。
                        
                        他走到我们面前，居高临下地看着我们。油彩掩盖了他的面容，但掩盖不住那双眼睛里透出的冰冷和锐利，那是一种视生命如草芥的眼神，我从未在任何人眼中看到过如此纯粹的杀气。
                        
                        他扫了我们一眼，然后用低沉而标准的中文问道：“谁是头？”
                        我们几个学生吓得瑟瑟发抖，没人敢说话。
                        他的目光在我们脸上逡巡，最后落在了我身上。不知道为什么，我觉得他的目光在我这里停留得稍久了一些。
                        “你们，”他再次开口，声音没有任何起伏，“惹上大麻烦了。”
                        后来我才知道，我们误闯的不是什么普通匪帮的地盘，而是一个跨国毒枭的运毒通道。而救下我们的，也不是什么军队或警察。
                        他们，就是“狼群”。而我的人生，从与那个高大男人——狼群副队长“骑士”对视的那一刻起，就彻底改变了轨迹。
                        """, videoOptions)
        );

        try {
            Thread.sleep(60 * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String imageUrl = response.getResult().getOutput().getVideoUrl();
        System.out.println(imageUrl);
    }


    /**
     * 语音生成文字
     */
    @SneakyThrows
    @GetMapping("test_audio2Text")
    public void testAudio2Text() {
        DashScopeAudioTranscriptionOptions transcriptionOptions = DashScopeAudioTranscriptionOptions
                .builder()
                .withModel("qwen3-asr-flash")
                .build();

        AudioTranscriptionResponse response = dashScopeAudioTranscriptionModel.call(
                new AudioTranscriptionPrompt(new UrlResource("https://dashscope.oss-cn-beijing.aliyuncs.com/audios/welcome.mp3"), transcriptionOptions)
        );

        System.out.println(response.getResult().getOutput());
    }
}