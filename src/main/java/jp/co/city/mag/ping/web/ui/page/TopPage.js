(function() {
    $(function() { initialize(); });

    function initialize() {
        setBootstrapSwitch();
    }
})();

function setBootstrapSwitch() {
    $('div.categories input[type="checkbox"]').bootstrapSwitch(); // この１行が必要だということがどこにも書いていない・・・公式サイトにすらも・・・
}
