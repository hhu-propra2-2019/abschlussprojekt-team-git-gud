var colors = [
    {id: 1, name: 'red'},
    {id: 2, name: 'orange'},
    {id: 3, name: 'yellow'},
    {id: 4, name: 'green'},
    {id: 5, name: 'blue'},
    {id: 6, name: 'indigo'},
    {id: 7, name: 'violet'},
    {id: 8, name: 'black'},
    {id: 9, name: 'white'},
    {id: 10, name: 'brown'},
    {id: 11, name: 'pink'}
];
var jp = new Tokenfield({
    el: document.querySelector('#input-tags'),
    items: colors,
    newItems: true,
    addItemOnBlur: true,
    filterSetItems: false,
    addItemsOnPaste: true,
    delimiters: [',', ' '],
    minChars: 0,
    maxSuggestWindow: 5,
    singleInput: true,
    singleInputValue: 'name'
});