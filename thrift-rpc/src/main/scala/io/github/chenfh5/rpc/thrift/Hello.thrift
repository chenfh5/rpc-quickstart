namespace java io.github.chenfh5.rpc.thrift.autogen

struct Message {
    1: i32 type;
    2: string data;
}

struct Response {
    1: i32 code;
    2: Message message;
}

service Hello {
    string helloString(1: string param)
    i32 helloInt(1: i32 param)
    bool helloBoolean(1: bool param)
    void helloVoid()
    Response sendMessage(1: Message message)
}