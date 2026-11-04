#!/usr/bin/env python3
import json, os

BASE = os.path.join(os.path.dirname(__file__), "..",
    "common/src/main/resources/assets/visual_keybinds/layouts/physical")

K = "key.keyboard."

def key(id, x, y, w=1.0, h=1.0):
    return {"id": K + id if not id.startswith(K) else id,
            "uX": float(x), "uY": float(y), "uWidth": float(w), "uHeight": float(h)}

def row(y, x, keys, w=1.0, h=1.0):
    out, cx = [], x
    for kid in keys:
        if isinstance(kid, tuple):
            kid, kw = kid
            out.append(key(kid, cx, y, kw, h))
            cx += kw
        else:
            out.append(key(kid, cx, y, w, h))
            cx += w
    return out

DIGITS = ["1","2","3","4","5","6","7","8","9","0"]
QWERTY = ["q","w","e","r","t","y","u","i","o","p"]
ASDF = ["a","s","d","f","g","h","j","k","l"]
ZXCV = ["z","x","c","v","b","n","m"]

# ---------------------------------------------------------------- ANSI block
def ansi_rows(y0, esc=False, grave=True):
    r = []
    r0 = ([("escape",1.0)] if esc else []) + ([("grave.accent",1.0)] if grave else []) + DIGITS + ["minus","equal"]
    if esc and grave:
        r += row(y0, 0, r0 + [("backspace",2.0)])
    else:
        r += row(y0, 0, r0 + [("backspace",2.0)])
    r += row(y0+1, 0, [("tab",1.5)] + QWERTY + ["left.bracket","right.bracket",("backslash",1.5)])
    r += row(y0+2, 0, [("caps.lock",1.75)] + ASDF + ["semicolon","apostrophe",("enter",2.25)])
    r += row(y0+3, 0, [("left.shift",2.25)] + ZXCV + ["comma","period","slash",("right.shift",2.75)])
    r += row(y0+4, 0, [("left.control",1.25),("left.win",1.25),("left.alt",1.25),("space",6.25),
                        ("right.alt",1.25),("right.win",1.25),("menu",1.25),("right.control",1.25)], w=1.25)
    return r

# ---------------------------------------------------------------- ISO block
def iso_rows(y0, esc=False, grave=True):
    r = []
    r0 = ([("escape",1.0)] if esc else []) + ([("grave.accent",1.0)] if grave else []) + DIGITS + ["minus","equal"]
    r += row(y0, 0, r0 + [("backspace",2.0)])
    r += row(y0+1, 0, [("tab",1.5)] + QWERTY + ["left.bracket","right.bracket"])
    # ISO enter spans tab row + home row; extra ISO key next to enter
    r += row(y0+2, 0, [("caps.lock",1.75)] + ASDF + ["semicolon","apostrophe","world.2"])
    r.append(key("enter", 13.75, y0+1, 1.25, 2.0))
    r += row(y0+3, 0, [("left.shift",1.25),"world.1"] + ZXCV + ["comma","period","slash",("right.shift",2.75)])
    r += row(y0+4, 0, [("left.control",1.25),("left.win",1.25),("left.alt",1.25),("space",6.25),
                        ("right.alt",1.25),("right.win",1.25),("menu",1.25),("right.control",1.25)], w=1.25)
    return r

# ---------------------------------------------------------------- JIS block
def jis_rows(y0, esc=False, grave=True):
    r = []
    r0 = ([("escape",1.0)] if esc else []) + ([("grave.accent",1.0)] if grave else []) + DIGITS + ["minus","equal","world.1"]
    r += row(y0, 0, r0 + [("backspace",1.0)])
    r += row(y0+1, 0, [("tab",1.5)] + QWERTY + ["left.bracket","right.bracket"])
    # JIS enter spans tab row + home row; extra JIS key (]) next to apostrophe-slot key
    r += row(y0+2, 0, [("caps.lock",1.75)] + ASDF + ["semicolon","apostrophe","world.2"])
    r.append(key("enter", 13.75, y0+1, 1.25, 2.0))
    # JIS "ro" key (backslash/underscore, scancode 0x73) between slash and right shift
    r += row(y0+3, 0, [("left.shift",2.25)] + ZXCV + ["comma","period","slash","backslash",("right.shift",1.75)])
    r += row(y0+4, 0, [("left.control",1.25),("left.win",1.25),("left.alt",1.25),("space",6.25),
                        ("right.alt",1.25),("right.win",1.25),("menu",1.25),("right.control",1.25)], w=1.25)
    return r

# ---------------------------------------------------------------- 60 percent
def layout_60(std):
    if std == "ansi": return ansi_rows(0, esc=True, grave=False), 15.0
    if std == "iso":  return iso_rows(0, esc=True, grave=False), 15.0
    return jis_rows(0, esc=True, grave=False), 15.0

# ---------------------------------------------------------------- F row / nav / numpad
def frow_tkl(y=0):
    # Esc with 1u gap before F1; 0.5u gaps between the F-key groups (TKL/full-size)
    return row(y, 0, ["escape"]) + \
           row(y, 2, ["f1","f2","f3","f4"]) + \
           row(y, 6.5, ["f5","f6","f7","f8"]) + \
           row(y, 11, ["f9","f10","f11","f12"]) + \
           row(y, 15.5, ["print.screen","scroll.lock","pause"])

def nav_tkl(y):
    return row(y, 15.5, ["insert","home","page.up"])

def nav_tkl2(y):
    return row(y, 15.5, ["delete","end","page.down"])

def arrows_tkl(y_up, y_bottom):
    return row(y_up, 16.5, ["up"]) + row(y_bottom, 15.5, ["left","down","right"])

def numpad(y0):
    r = []
    r += row(y0,   19, ["num.lock","keypad.divide","keypad.multiply","keypad.subtract"])
    r += row(y0+1, 19, ["keypad.7","keypad.8","keypad.9"])
    r.append(key("keypad.add", 22, y0+1, 1.0, 2.0))
    r += row(y0+2, 19, ["keypad.4","keypad.5","keypad.6"])
    r += row(y0+3, 19, ["keypad.1","keypad.2","keypad.3"])
    r.append(key("keypad.enter", 22, y0+3, 1.0, 2.0))
    r += row(y0+4, 19, [("keypad.0",2.0),"keypad.decimal"])
    return r

# ---------------------------------------------------------------- TKL
def layout_tkl(std):
    keys = frow_tkl(0)
    keys += globals()[std + "_rows"](1, grave=True)
    keys += nav_tkl(1) + nav_tkl2(2)
    keys += arrows_tkl(4, 5)
    return keys, 18.5

# ---------------------------------------------------------------- 100
def layout_100(std):
    keys, w = layout_tkl(std)
    keys += numpad(1)
    return keys, 23.0

# ---------------------------------------------------------------- 75
def layout_75(std):
    # QMK LAYOUT_75_ansi: 16u wide, contiguous F-row, right nav column at x15
    keys = row(0, 0, ["escape","f1","f2","f3","f4","f5","f6","f7","f8","f9","f10","f11","f12",
                      "print.screen","scroll.lock","delete"])
    if std == "ansi":
        keys += row(1, 0, ["grave.accent"] + DIGITS + ["minus","equal",("backspace",2.0)])
        keys.append(key("home", 15, 1))
        keys += row(2, 0, [("tab",1.5)] + QWERTY + ["left.bracket","right.bracket",("backslash",1.5)])
        keys.append(key("page.up", 15, 2))
        keys += row(3, 0, [("caps.lock",1.75)] + ASDF + ["semicolon","apostrophe",("enter",2.25)])
        keys.append(key("page.down", 15, 3))
        keys += row(4, 0, [("left.shift",2.25)] + ZXCV + ["comma","period","slash",("right.shift",1.75),"up","end"])
        keys += row(5, 0, [("left.control",1.25),("left.win",1.25),("left.alt",1.25),("space",6.25),
                            "right.alt","right.win","right.control","left","down","right"])
    else:
        grave_row = ["grave.accent"] + DIGITS + ["minus","equal"]
        if std == "iso":
            keys += row(1, 0, grave_row + [("backspace",2.0)])
            keys.append(key("home", 15, 1))
            keys += row(2, 0, [("tab",1.5)] + QWERTY + ["left.bracket","right.bracket"])
            keys += row(3, 0, [("caps.lock",1.75)] + ASDF + ["semicolon","apostrophe","world.2"])
            keys.append(key("enter", 13.75, 2, 1.25, 2.0))
            keys.append(key("page.up", 15, 2))
            keys.append(key("page.down", 15, 3))
            keys += row(4, 0, [("left.shift",1.25),"world.1"] + ZXCV + ["comma","period","slash",("right.shift",1.75),"up","end"])
        else:  # jis
            keys += row(1, 0, grave_row + ["world.1",("backspace",1.0)])
            keys.append(key("home", 15, 1))
            keys += row(2, 0, [("tab",1.5)] + QWERTY + ["left.bracket","right.bracket"])
            keys += row(3, 0, [("caps.lock",1.75)] + ASDF + ["semicolon","apostrophe","world.2"])
            keys.append(key("enter", 13.75, 2, 1.25, 2.0))
            keys.append(key("page.up", 15, 2))
            keys.append(key("page.down", 15, 3))
            # ro key (backslash/underscore) between slash and right shift, right shift shrunk to 1.75u
            keys += row(4, 0, [("left.shift",2.25)] + ZXCV + ["comma","period","slash","backslash",("right.shift",1.75),"end"])
            # JIS 75: shorter spacebar so the arrow row fits left/up/down/right on the bottom row
            keys += row(5, 0, [("left.control",1.25),("left.win",1.25),("left.alt",1.25),("space",5.25),
                                "right.alt","right.win","right.control","left","up","down","right"])
            return keys, 16.0
        keys += row(5, 0, [("left.control",1.25),("left.win",1.25),("left.alt",1.25),("space",6.25),
                            "right.alt","right.win","right.control","left","down","right"])
    return keys, 16.0

# ---------------------------------------------------------------- build + validate
def validate(name, keys, width):
    for k in keys:
        assert 0 <= k["uX"] and k["uX"] + k["uWidth"] <= width + 1e-9, (name, k)
    for i, a in enumerate(keys):
        for b in keys[i+1:]:
            ox = a["uX"] < b["uX"] + b["uWidth"] and b["uX"] < a["uX"] + a["uWidth"]
            oy = a["uY"] < b["uY"] + b["uHeight"] and b["uY"] < a["uY"] + a["uHeight"]
            assert not (ox and oy), ("overlap", name, a, b)
    ids = [k["id"] for k in keys]
    assert len(ids) == len(set(ids)), ("duplicate ids", name)
    print("%-12s keys=%3d width=%s" % (name, len(keys), width))

def build(std, size):
    if size == "60": keys, w = layout_60(std)
    elif size == "75": keys, w = layout_75(std)
    elif size == "tkl": keys, w = layout_tkl(std)
    else: keys, w = layout_100(std)
    validate("%s_%s" % (std, size), keys, w)
    path = os.path.join(BASE, std, "%s_%s.json" % (std, size))
    with open(path, "w") as f:
        json.dump({"totalWidthU": w, "keys": keys}, f, indent=2)
        f.write("\n")

for std in ["ansi", "iso", "jis"]:
    for size in ["60", "75", "tkl", "100"]:
        build(std, size)
print("done")
