function D(params){
    return function(d, i){
        if(typeof params ==='function') { return params(d) }
        else if( typeof params ==='string'){ return (new Function( 'return (' + d + params + ')' )()) }
        else { return d};
    }
}

function I(params){
    return function(d, i){
        if(typeof params ==='function') { return params(i) }
        else if( typeof params ==='string'){ return (new Function( 'return (' + i + params + ')' )()) }
        else { return i};
    }
}

function F(name){
  var params=Array.prototype.slice.call(arguments,1);
    return function(d){
        if(typeof params[0] ==='function') { return params[0](d[name]) }
        else if( typeof params[0] ==='string'){ return (new Function( 'return (' + d[name] + params[0] + ')' )()) }
        else if( typeof name === 'object' ){ return name }
        else { return d[name]};
    }
}




d3.svg('Language.csv', function(csv){
    var w = 800;
    var h = 800;

    var svg = d3.select('svg');

    var data = { //csvデータを一つのchidrenとしてデータセットを作成
        children:csv
    };

    //各要素のattrの内容（変数にしておくとアップデートを行うときに便利)
    var circleAttr = {
        cx: F('x'),
        cy: F('y'),
        r: F('r')
    };
    var labelAttr = {
        x: F('x'),
        y: F('y')
    };
    var countAttr = {
        x: F('x'),
        y: F('y', ' + 20')
    };

    var pack = d3.layout.pack().size([w,h]);  //バブルチャートで使用するpackレイアウトオブジェクトを作成

    var retSpeakers  = function(d){ return d.Speakers };   //Speakersの値を返す関数
    var reTotalCountries = function(d){ return d.TotalCountries };　//TotalCountriesの値を返す関数

    var circleGroup = svg.selectAll("g")
	.data(pack.value(retSpeakers).nodes(data)) //データセットからspeakersの値を元にレイアウトを作成する
	.enter()
	.append("g")
	.filter(function(d){
	    return d.Speakers > 0; //speakersの値が無い要素(root)は描画しない
	}) ;

    var circle = circleGroup.append('circle') //各サークル作成
	.attr({
	    class:F('name'),
	    "fill-opacity":0.8,
	    "stroke-width":1,
	    fill: function(d){return (d.LangName==='日本語') ? "red" : " blue " ; },  //日本だけ赤く塗る
	    stroke:function(d){return "white";},
	})
	.attr(circleAttr)

    var label = circleGroup.append('text') //ラベル(名称)作成
        .attr({
            fill: "white",
            "font-size": "12px",
            "text-anchor": "middle",
            "alignment-baseline": "middle"
        })
        .attr(labelAttr)
        .text(F('LangName'))

    var count = circleGroup.append('text') //ラベル(数値)作成
        .attr({
            fill: "white",
            "font-size": "12px",
            "text-anchor": "middle",
            "alignment-baseline": "middle",
        })
        .attr(countAttr)
        .text(F('Speakers', " / 1000000000 + '億人'")) //デフォルトは使用人口を表示

    var title =  svg.append('text') //タイトル
        .attr({
            fill:'black',
            'font-size':'24',
            x: 40,
            y: 100
        })
        .text('Speakers')

    var style1 = function() { //使用人口表示
        circleGroup.data(pack.value(retSpeakers).nodes(data));
        circle.transition().attr(circleAttr).duration(1000);
        label.transition().attr(labelAttr).duration(1000);
        count.transition().attr(countAttr).text(F('Speakers', " / 1000000000 + '億人'")).duration(1000);
        title.text('Speakers')
    }
    var style2 = function() { //使用国数表示
        circleGroup.data(pack.value(reTotalCountries).nodes(data));
        circle.transition().attr(circleAttr).duration(1000);
        label.transition().attr(labelAttr).duration(1000);
        count.transition().attr(countAttr).text(F('TotalCountries', "  + 'ヶ国'")).duration(1000);
        title.text('Total Countries')
    }

    d3.select('body').on('click', toggle(style2, style1) ); //クリックでスタイル切り替え
});